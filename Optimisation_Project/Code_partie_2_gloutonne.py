import pandas as pd
import time

# Import local
from settings import DATA_PATH, FILE 

# PARAMETRES

e_min = 2 # Nombre d'exemplaires MIN par lot
e_max = 4 # Nombre d'exemplaires MAX par lot
L_max = 15 # Nombre de types de lots différents maximum
C_d = 0.13 # Cout de conditionnement d'un t-shirt
r_min = 15 # Indice commercial MIN par lot

# POSTES ET CAPACITÉS

p1 = [1, 50000]
p2 = [2, 50000]
p3 = [3, 60000]
p4 = [4, 60000]
p5 = [5, 70000]

liste_poste = [p5,p4,p3,p2,p1] # On commencera par le poste avec la capacité la plus grande

# ARTICLES

df_articles = pd.read_excel(f'{DATA_PATH}\\{FILE}')
df_articles = df_articles.sort_values(by='Prix Vente', ascending=False) # On tri du plus grand au plus petit par Prix de vente unitaire
df_articles.reset_index()
# LOT

liste_lots = [] # Taille MAX = 15

# PROFIT A MAXIMISER ET SOLUTION

profit = 0
solutions = [] # liste de listes avec les solutions par poste [[numero_poste, nbre_lot, nbre_article_par_lot], ...]

def parcours(set_articles, lot, index):
    '''
    Fonction qui parcours les articles et ajoute l'article dans le lot

    args:

        set_articles (DataFrame) : dataframe des articles contenant 4 colonnes : 
                                - "Article" : le numéro d'un article
                                - "#Article" : le nombre max d'un article
                                - "Prix Vente" : le prix unitaire d'un article
                                - "Indice" : l'indice commercial d'un article
        
        lot (dict) : dictionnaire initialisé -> {'Liste lot': [], 'Indice' : 0, 'Prix de vente unitaire' : 0}
        
        index (int) : index de l'article 

    return:

        set_articles (DataFrame)
        lot (dict)

    '''

    lot['Liste lot'].append(set_articles['Article'][index])
    lot['Indice'] += set_articles['Indice'][index]
    lot['Prix de vente unitaire'] += set_articles['Prix Vente'][index] - 0.13
    lot['Prix de vente unitaire'] = round(lot['Prix de vente unitaire'],2)

    return set_articles, lot

def parcours_articles_lot_non_vide(set_articles, lot, index):
    '''
    Fonction qui parcours les articles quand le lot n'est pas vide

    args:

        set_articles (DataFrame) : dataframe des articles contenant 4 colonnes : 
                                - "Article" : le numéro d'un article
                                - "#Article" : le nombre max d'un article
                                - "Prix Vente" : le prix unitaire d'un article
                                - "Indice" : l'indice commercial d'un article
        
        lot (dict) : dictionnaire initialisé -> {'Liste lot': [], 'Indice' : 0, 'Prix de vente unitaire' : 0}
        
        index (int) : index de l'article != 0

    return:

        set_articles (DataFrame)
        lot (dict)

    '''
    set_articles, lot = parcours(set_articles, lot, index)
    set_articles.drop(index, inplace=True)
    set_articles = set_articles.reset_index(drop=True)

    return set_articles, lot

def parcourir_articles_lot_vide(set_articles, lot):
    '''
    Fonction lancée dans le cas où un nouveau lot est initialisé et qu'il n'y a aucun article dedans.
    Elle permet de parcourir les articles et d'ajouter le premier du dataframe

    args:

        set_articles (DataFrame) : dataframe des articles contenant 4 colonnes : 
                                - "Article" : le numéro d'un article
                                - "#Article" : le nombre max d'un article
                                - "Prix Vente" : le prix unitaire d'un article
                                - "Indice" : l'indice commercial d'un article
        
        lot (dict) : dictionnaire initialisé -> {'Liste lot': [], 'Indice' : 0, 'Prix de vente unitaire' : 0}

    return:

        set_articles (DataFrame)
        lot (dict)
        nombre_article (int) : quantité max de l'article
    '''
    set_articles, lot = parcours(set_articles, lot, 0)
    nombre_article = set_articles['#Article'][0]                    # On enregistre la quantité max à produire de l'article
    lot['Nombre d unité max du lot'] = nombre_article
    set_articles = set_articles.drop(0)                             # On supprime l'article du dataframe
    set_articles = set_articles.reset_index(drop=True)              # On reset les index pour facilité le parcours du dataframe par la suite

    return set_articles, lot, nombre_article



def parcourir_articles(set_articles, lot, nombre_article, p):
    '''
    Fonction lancée dans le cas où un lot contient au moins 1 article

    args:

        set_articles (DataFrame) : dataframe des articles contenant 4 colonnes : 
                                - "Article" : le numéro d'un article
                                - "#Article" : le nombre max d'un article
                                - "Prix Vente" : le prix unitaire d'un article
                                - "Indice" : l'indice commercial d'un article
        
        lot (dict) : dictionnaire initialisé -> {'Liste lot': [], 'Indice' : 0, 'Prix de vente unitaire' : 0}

    return:

        set_articles (DataFrame)
        lot (dict)
        nombre_article (int) : quantité max de l'article
    '''
    for k in range(len(set_articles)):
               
        try:

            if k == 1 : # Dans le cas où il y a un élement dans le lot et que l'élement suivant dans le dataframe respecte les conditions d'ajout dans le meme lot                                                                                       

                set_articles = set_articles.reset_index(drop=True)
                
                if  set_articles['#Article'][0] >= nombre_article and len(lot['Liste lot']) < 4: # Si le nombre d'article de l'élement est supérieur ou égal au premier article du lot
                                                                                                 # ET que le lot contient moins de 4 articles
                    set_articles, lot = parcours_articles_lot_non_vide(set_articles, lot, 0)

                    return set_articles, lot, p

                # if len(set_articles) < 8 :
                    
                #     if  len(lot['Liste lot']) < 4:
                    
                #         set_articles, lot = parcours_articles_lot_non_vide(set_articles, lot, k)
                    
                #     return set_articles, lot, p

            if  set_articles['#Article'][k] >= nombre_article and len(lot['Liste lot']) < 4:
                
                set_articles, lot = parcours_articles_lot_non_vide(set_articles, lot, k)
                
                return set_articles, lot, p

        except KeyError:
            pass

        if len(lot['Liste lot']) == 4 :
            p = 4
            return set_articles, lot, p 
    
    p = 4
    return set_articles, lot, p 




def greedy(articles):

    """Cette fonction nous permet de constituer des lots en respecant les contraintes:
         -> 2 <= taille lot <= 4
         -> indice lot >= 15
         Cette fonction gloutonne ne ressort pas la solution optimale ! 
         Mais cette solution est basée sur le Prix de vente unitaire des lots.

    Args:
        articles (DataFrame): dataframe des articles contenant 4 colonnes : 
                                - "Article" : le numéro d'un article
                                - "#Article" : le nombre max d'un article
                                - "Prix Vente" : le prix unitaire d'un article
                                - "Indice" : l'indice commercial d'un article

    return:
        liste_lots (list) : liste de dictionnaire de lots avec leur indice et Prix de vente unitaire cumulés
    """

    set_articles = articles

    for j in range(15):
        
        lot = {'Liste lot': [], 'Indice' : 0, 'Prix de vente unitaire' : 0, 'Nombre d unité max du lot' : 0, 'Traité' : 0}
     
        set_articles = set_articles.reset_index(drop=True)  

        p = 0

        if set_articles.empty: #Quand le dataframe est vide on arrete la fonction
            break 
        
        if len(lot['Liste lot']) == 0 :
            
            set_articles_first, lot, nombre_article = parcourir_articles_lot_vide(set_articles, lot)
            
        while p != 4:
            
            set_articles_first = set_articles_first.reset_index(drop=True)
            set_articles, lot, p = parcourir_articles(set_articles_first, lot, nombre_article, p)
            set_articles_first = set_articles

        if not lot in liste_lots: 
            liste_lots.append(lot)
        
        j += 1

    liste_lots_sort = [element for element in liste_lots if element['Indice'] >= 15] # On garde uniquement les lots avec un indice supérieur à 15
    
    return liste_lots_sort

def new_dataframe(liste_lots_sort, articles):
    '''
    Cette fonction met à jour le dataframe en retirant les articles où toutes leur quantité est utilisée

    Args:
        liste_lots_sort (list) : liste de dictionnaire de lots avec leur indice et Prix de vente unitaire cumulés triées
        articles (DataFrame): dataframe des articles contenant 4 colonnes : 
                                - "Article" : le numéro d'un article
                                - "#Article" : le nombre max d'un article
                                - "Prix Vente" : le prix unitaire d'un article
                                - "Indice" : l'indice commercial d'un article

    return:
        articles_restants (DataFrame) : DataFrame mis à jour

    '''
    list_articles = []
    list_nbrearticle = []
    list_prix_vente = []
    list_indice = []

    for element in liste_lots_sort:
        
        for i in range(len(element['Liste lot'])):
            
            if element['Liste lot'][i] in articles['Article'] and i != 0:
                
                for j in range(len(articles)):

                    if element['Liste lot'][i] == articles['Article'][j] and not articles['Article'][j] in list_articles :
                        
                        if element['Traité'] == 0 :

                            list_nbrearticle.append(articles['#Article'][j] - element['Nombre d unité max du lot'])
                            list_articles.append(articles['Article'][j])                
                            list_prix_vente.append(articles['Prix Vente'][j])
                            list_indice.append(articles['Indice'][j])

    for i in range(len(liste_lots_sort)):

        liste_lots_sort[i]['Traité'] = 1

    articles_restant = pd.DataFrame({'Article':list_articles,'#Article':list_nbrearticle,'Prix Vente':list_prix_vente,'Indice':list_indice})
    
    return articles_restant

def machine_total(dict_articles, liste_poste):
    '''
    Cette fonction attribu un poste à un lot

    Vu que la totalité des lots ne dépassent pas 70k ils ont tous le poste 1

    '''
    for machine in liste_poste:

        k = 0

        for element in dict_articles:

            if element['Nombre d unité max du lot'] <= machine[1] - k:

                k += element['Nombre d unité max du lot']
                element['Poste'] = machine[0]
    
    total = 0

    for element in dict_articles:

        total += element['Nombre d unité max du lot']*element['Prix de vente unitaire']

    return dict_articles, total


start = time.time()

first = greedy(df_articles)

second_df= new_dataframe(first, df_articles)
second_df = second_df.sort_values(by='Prix Vente', ascending=False) # On tri du plus grand au plus petit par Prix de vente unitaire
second_df = second_df.reset_index(drop=True)

second = greedy(second_df)

third_df= new_dataframe(second, second_df)
third_df = third_df.sort_values(by='Prix Vente', ascending=False) # On tri du plus grand au plus petit par Prix de vente unitaire
third_df = third_df.reset_index(drop=True)

third = greedy(third_df)

forth_df= new_dataframe(third, third_df)
forth_df = forth_df.sort_values(by='Prix Vente', ascending=False) # On tri du plus grand au plus petit par Prix de vente unitaire
forth_df = forth_df.reset_index(drop=True)

forth = greedy(third_df)

final, total = machine_total(forth, liste_poste)
print(f'Solution finale:{final}, \n Pour un total de bénéfices de {round(total)} €')