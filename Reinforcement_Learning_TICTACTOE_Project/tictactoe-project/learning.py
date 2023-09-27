import json
import numpy as np
import re as re
from tqdm import tqdm
from utils import test_finish

#Local imports
from settings import PLAYERS_DATA_TEST_PATH


####################################################
# GENERATION DU DICTIONNAIRE AVEC LES COMBINAISONS #
####################################################

StatesMatrix = np.zeros((3**9,9))
for i in range(3**9):
     c = i
     for j in range(9):
       StatesMatrix[i][j] = c % 3
       c //= 3
StatesMatrix1 = np.zeros((18364,9))
k = 0
for i in range(0,StatesMatrix.shape[0]):
  if (np. count_nonzero(StatesMatrix[i] == 1) - np. count_nonzero(StatesMatrix[i] == 2)) == 1 or (np. count_nonzero(StatesMatrix[i] == 1) - np. count_nonzero(StatesMatrix[i] == 2)) == 0:
    StatesMatrix1[k] = StatesMatrix[i]
    k += 1
combinaisons = []
for element in StatesMatrix1:
    combinaison = ''    
    for i in range(len(element)):
        element_str = [str(j) for j in element ]
        for j in range(len(element)):

            if element_str[j] == '1.0':
                element_str[j] = 'x'
                
            elif element_str[j] == '2.0':
                element_str[j] = 'o'
            else:
                element_str[j] = '-'
        element_str_join = ''.join(element_str)
    combinaisons.append(element_str_join) #probleme de taill on est a 19k pas 5k


def next_move(board,dic,eps) : 
    """This function return the next move of the IA

    Args:
        board (str): the board : 'x--o-----' for example
        dic (dict): dict of all combinaison
        eps (float): epsilon

    Returns:
        str: next board 
    """
    print(type(board), board)
    possible_moves = [m.start() for m in re.finditer('-', board) ]
    if np.random.uniform() > eps:
        l_move=[str(e+1) for e in possible_moves ]
        dict_move={key: dic['data'][board][key] for key in l_move}
        move=int(max(dict_move, key=lambda k: dict_move[k], default=''))-1

    else :
        move = np.random.choice(possible_moves)
    
    return move



def cal_reward(board,player) : 
    """Function which calculate the reward for a board state 

    Args:
        board (str): game board
        player (str): x or o
    """
    print(type(player), player)
    if (test_finish(board)=='x' and player=='x') or (test_finish(board)=='o' and player=='o') :
        reward=20
        
    elif (test_finish(board)=='o' and player=='x') or (test_finish(board)=='x' and player=='o') :
        reward=-10
    
    elif test_finish(board)=="draw" :
        reward=-1
    
    else :
        reward=0
    return(reward)


def create_dict(list_combinaison):
    """
    These function create 3 dictionnaries : 
        the dict which contains values of Q-function (dict_)
        the dict which contains rewards of each board states if the player is 'x' (dict_reward) 
        the dict which contains rewards of each board states if the player is 'o' (dict_reward2)

    Args:
        list_combinaison (list) : list with all board combinaisons

    Return:
        dict_ (dict) : the first dict
        dict_reward (dict) : dict_reward for x
        dict_reward2 (dict) : dict_reward for o
    """   
    
    dict_ = {
        "type": "Q",
        "data": {}
        }
    
    dict_reward= {
        "type": "Q",
        "reward": {}
        }
    dict_reward2= {
        "type": "Q",
        "reward": {}
        }

    for element in list_combinaison:
        dict_['data'][element] = {'1':0,'2':0,'3':0,'4':0,'5':0,'6':0,'7':0,'8':0,'9':0}
        dict_reward['reward'][element]=cal_reward(element,'x')
        dict_reward2['reward'][element]=cal_reward(element,'o')

    return dict_,dict_reward,dict_reward2


def train_tictactoe(dic,dicreward,dicreward2,nb_train,gamma,alpha,eps) : 
    """Training function

    Args:
        dic (dict): dictionnary with all combinaison + rewards
        dicreward (dict) : dict of reward for x
        dicreward2 (dict): dict of reward for o
        nb_train (int): number of game
        gamma (float): parameter
        alpha (float): parameter
        eps (float): parameter
    """
    player1='x'
    player2='o'
    for i in range(nb_train) :
        board="---------"

        while (test_finish(board)==None) :
            move=next_move(board,dic,eps)
            next_board=board[:move] + player1 + board[move+1:]
            dic['data'][board][str(move+1)]+=alpha*(dicreward['reward'][next_board]+gamma*max(dic['data'][next_board].values())-dic['data'][board][str(move+1)])  
            board=next_board
            if ("-" in board) :
                move= next_move(board,dic,eps)
                next_board=board[:move] + player2 + board[move+1:]
                dic['data'][board][str(move+1)]+=alpha*(dicreward2['reward'][next_board]+gamma*max(dic['data'][next_board].values())-dic['data'][board][str(move+1)])     
                board=next_board
        
        
    return(dic)

dict_,dict_reward,dict_reward2 = create_dict(combinaisons)

for i in tqdm(range(1000000),position = 0 ,leave=True):
    dict_ = train_tictactoe(dict_, dict_reward, dict_reward2, 1, 0.95, 0.95, 0.9)

with open(f"{PLAYERS_DATA_TEST_PATH}\\huang_lambert.json", "w") as outfile:
    json.dump(dict_, outfile)
