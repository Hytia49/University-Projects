"""All tic-tac-toe strategies are placed here.
"""
# Low-level modules
from typing import Dict

# Project modules
from utils import display, possible_move

class Strategy(object):
    """All playing strategies should derive from this"""

    def action(self, s: str, my_mark: str = "x") -> str:
        """Returns my best action on the board situation
        defined by s
        """
        raise NotImplementedError


class Dummy(Strategy):
    """Plays at random"""

    def action(self, s: str, my_mark: str = "x") -> str:
        """Plays one random move and returns new state"""
        import numpy as np
        import re

        # There should be at least one possible move
        assert "-" in s

        # Get all possible moves
        possible_moves = [m.start() for m in re.finditer("-", s)]

        # Choose one position at random
        move = np.random.choice(possible_moves)

        return s[:move] + my_mark + s[move + 1 :]


class SmartStart(Strategy):
    """Plays at random but has a smarter first move"""

    def action(self, s: str, my_mark: str = "x") -> str:
        """Plays one random move and returns new state,
        except for very first move where it plays at center mark.
        """
        import numpy as np
        import re

        # There should be at least one possible move
        assert "-" in s

        # Play center if very first move
        if not any([mark == my_mark for mark in s]) and s[4] == "-":
            move = 4
        else:
            # Get all possible moves
            possible_moves = [m.start() for m in re.finditer("-", s)]

            # Choose one position at random
            move = np.random.choice(possible_moves)

        return s[:move] + my_mark + s[move + 1 :]


class Human(Strategy):
    """Asks you to play!"""

    def action(self, s: str, my_mark: str = "x") -> str:
        """Asks you to play."""
        # display board (for human to decide)
        display(s)

        # Ask for action and check its feasibility
        move = -1
        while not possible_move(s, move):
            move = input(
                f'Où placer "{my_mark}" ?'
                " (de 1 en haut à gauche à 9 en bas à droite) :"
            )
            move = int(move) - 1

        return s[:move] + my_mark + s[move + 1 :]


class QStrategy(Strategy):
    """Implements a state-action value based strategy"""

    def __init__(self, Q: Dict):
        """Needs the Q-function to work (see README.md)"""
        self.Q = Q

    def action(self, s: str, my_mark: str = "x") -> str:
        """Finds best action."""
        # find best action (be aware it could be unfeasible)
        try:
            d = self.Q["data"][s]

        except KeyError:
            # if no info for this state, use random
            return Dummy().action(s, my_mark)

        no_feasible_move = True
        move = -1
        for move in sorted(d, key=d.get, reverse=True):
            # convert from str to int (and to base 0 instead of base 1)
            move = int(move) - 1

            # check feasibility
            if possible_move(s, move):
                no_feasible_move = False
                break

        assert not no_feasible_move, f"Could not find a feasible move for board {s}"

        return s[:move] + my_mark + s[move + 1 :]
        
def getMeiileurPosition(s,possible_moves, my_mark) -> int:
    data = [0,0,0,0,0,0,0,0,0]
    ligne1 = [0,1,2]
    ligne2 = [3,4,5]
    ligne3 = [6,7,8]
    colone1= [0,3,6]
    colone2= [1,4,7]
    colone3= [2,5,8]
    diagonale1=[0,4,8]
    diagonale2=[2,4,6]
    lignes =[ligne1,ligne2,ligne3,colone1,colone2,colone3,diagonale1,diagonale2]
    for x in possible_moves:
        #print("---------------------noed parent "+str(x))
        for ligne in lignes:
            #print("################liste des ligne :"+''.join(str(sssx) for sssx in ligne))
            #print(s)
            if x in ligne:
                niveau_ligne = 0
                #print("level Ligne0 :" +str(niveau_ligne))
                for position in ligne:
                    #print("............neud fils de la position :"+ str(position))
                    if s[position] == my_mark:
                        niveau_ligne+=1
                        #print("*my mark")
                    elif s[position] == "o":
                        niveau_ligne-=1
                        #print("#your mark")
                    #print("level Ligne1 :" +str(niveau_ligne))
                if niveau_ligne == 2:
                    data[x] = 999
                elif niveau_ligne == -2:
                    data[x] = -999
                elif abs(data[x]) != 999:
                    if not (abs(data[x]) == 1 and data[x] == -niveau_ligne):
                        if data[x] == 0 or (data[x] > 0 and data[x] < data[x]+niveau_ligne) or (data[x] < 0 and data[x] > data[x]+niveau_ligne):
                            data[x]+=niveau_ligne
                    else:
                        data[x] = abs(data[x])
                #print("level Ligne 2:" +str(niveau_ligne))
                #print(data)
                #print()
    max_value = max(data)
    min_value = min(data)
    print(data)
    #print(max_value)
    #print(min_value)
    if max_value < abs(min_value):
        #print("MIN____:"+str(min_value))
        return data.index(min_value)
    else:
        #print("MAX____:"+str(max_value))
        return data.index(max_value)
            
class MinMax(Strategy):
    """Plays min et max"""
    
    def action(self, s: str, my_mark: str = "x") -> str:
        import numpy as np
        import re

        # There should be at least one possible move
        assert "-" in s

        # Play center if very first move
        if not any([mark == my_mark for mark in s]) and s[4] == "-":
            move = 4
        else:
            ## Get all possible moves
            possible_moves = [m.start() for m in re.finditer("-", s)]
            print(possible_moves)
            ## Choose one position at random
            move = getMeiileurPosition(s,possible_moves, my_mark)
                
        return s[:move] + my_mark + s[move + 1 :]
