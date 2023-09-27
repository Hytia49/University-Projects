# Création d'un jeu de données 

# Différents imports
import pandas as pd
import numpy as np

# Import des données
df = pd.read_excel("BenchMarksVE.xls")
df = pd.DataFrame(df)

# Définition des valeurs
d = df["#Article"]
r = df["Indice"]
v = df["Prix Vente"]
emin = int(df[['Valeur']].iloc[0])
emax = int(df[['Valeur']].iloc[1])
lmax = int(df[['Valeur']].iloc[2])
cd = df[['Valeur']].iloc[3]
rmin = int(df[['Valeur']].iloc[4])
C = df["Capacité"]
C.dropna()

# Heuristique sur mesure

# Knapsack 
import knapsack

# Application de l'algorithme répété Lmax fois
# Définition des variables :
val = [1]*len(d)
nb_it = 0
liste_lots = []
prod_lots = []
while nb_it < lmax :
    res, liste = knapsack.knapsack(val, v.tolist()).solve(int(emax))
    
    # Vérification que la taille du type de lot est assez grande (emin)
    if len(liste) > emin :
        # Vérification que l'indicage est assez grande (rmin)
        ind = 0
        for j in range (len(liste)):
            ind += r[liste[j]]
        if ind >= rmin :
            # Nous gardons la solution
            nb_it += 1
            # Nous maximison la production du type de lot
            liste_comp = []
            for i in range (len(liste)):
                liste_comp.append(d[liste[i]])
            prod = min(liste_comp)
            # Ajout de la production de la liste 
            liste_lots.append(liste)
            prod_lots.append(prod)
            # Mise à jour des liste
            for i in range (len(liste)):
                # Nous retirons des disponbilités les t-shirts utilisés
                d[liste[i]] -= prod
                # Nous mettons à 0 le prix des t-shirts indisponible
                if d[liste[i]] == 0 :
                    v[[liste[i]]] = 0


# Affichage des résultats de l'heuristique sur-mesure
print(liste_lots)
print(prod_lots)

for i in range (len(liste_lots)) :
    print(liste_lots[i])
    print(prod_lots[i])

# Nous dispachons les lots
repart_cond = []
reste_conditionner = prod_lots
# Nous traversons toutes les machines
for i in range (len(C.dropna())):
    repart_cond.append([])
    # Nous définissons l'espace libre dont nous disposons pour conditionner les t-shirts à la semaine
    espace_libre = C[i]
    # Nous traversons les résultats pour les attribuer
    for j in range (len(liste_lots)):
        repart_cond[i].append(0)  
        # Nous vérifions qu'il y a assez d'espace pour tout mettre ...
        if reste_conditionner[j]*len(liste_lots[j]) <= espace_libre :
            repart_cond[i][j] = reste_conditionner[j]*len(liste_lots[j])
            espace_libre -= reste_conditionner[j]*len(liste_lots[j])
            reste_conditionner[j] = 0
        # Ou bien nous ne mettons qu'une partie
        elif espace_libre > 0 :    
            mis = int(espace_libre/len(liste_lots[j]))
            reste_conditionner[j] -= mis
            repart_cond[i][j] = mis*len(liste_lots[j])
            espace_libre = 0
# S'il n'y a plus de place dans le conditionnement nous réduisons la production d'autant que le surplus
while reste_conditionner.count(0) < len(reste_conditionner) :
    del liste_lots[len(liste_lots)]
    del prod_lots[len(prod_lots)]
print(repart_cond)

# Import des données pour la fonction objective
df = pd.read_excel("BenchMarksVE.xls")
df = pd.DataFrame(df)
v = df["Prix Vente"]
d = df["#Article"]

# Borne suppérieure trivial 
def borne_sup_trivial(d, v):
    res = 0
    for i in range (len(d)):
        res +=d[i]*(v[i]-cd)
    return res
print("La borne suppérieure triviale est : ",borne_sup_trivial(d, v))

# Borne lagrangienne :
def borne_lagrangienne(liste_lots, prod_lots, lamb, rho, phi):
    res = 0
    for i in range (len(liste_lots)):
        for j in range (len(liste_lots[i])):  
            res += (v[liste_lots[i][j]] -cd)*prod_lots[i] - lamb + rho + phi*r[j]
    res += lamb*emin - rho*emax + phi * rmin
    return int(res)
print("La borne Lagrgangienne est de : ",borne_lagrangienne(liste_lots, prod_lots, 200, 300, 150), " euros")

# Fonction objective :
def gain_associé(liste_lots, prod_lots):
    res = 0
    for i in range (len(liste_lots)):
        for j in range (len(liste_lots[i])):  
            res += (v[liste_lots[i][j]] - cd)*prod_lots[i]
    return int(res)
print("Le résultat est de : ",gain_associé(liste_lots, prod_lots), " euros")

# Métaheuristique

# Import de package
import random
#Algorithme génétique
while len(liste_lots) >= lmax :
    
    # Définition du lot étudié qui est le dernier de la liste
    lot_etudie = liste_lots[len(liste_lots)-1]
    
    # Définition de la liste regroupant la liste des t-shirts à interchanger
    groupe_interchange = []
    while len(liste_lots) >= lmax :
        # Nous étudions chaque liste de lots sauf la dernière
        for i in range(len(liste_lots)-1):
        # Nous etudions liste_lots[i]

            # Définitions des productions
            prod_groupe_etudie = prod_lots[len(liste_lots)-1]
            prod_groupe_interchange = prod_lots[i]

            # Nous traversons les éléments du lots i
            for j in range (len(liste_lots[i])):
                # Vérification que l'échange est intéressant 
                for k in range (len(liste_lots[i])):
                    # Calcul de l'échange 
                    gain = gain_interchanger(liste_lots[i][j], liste_lots[len(liste_lots[i])-1][k], liste_lots[i], prod_lots[i], prod_lots[len(liste_lots[i])-1])
                    # Si léchange est intéressant ...
                    if gain :
                        # Alors nous inversons la production

                        # Changement des éléments
                        liste_lots[i] = inverse_deux_éléments(liste_lots[i][j], liste_lots[len(liste_lots[i])-1][k], liste_lots[i])
                        
                        # Changement des productions
                        # Si la nouvelle prod est plus faible, nous mettons à disposition les t-shirts de l'ancienne prod

                        # Définition de la nouvelle prod
                        prod_new = min(prod_lots[i], prod_lots[len(liste_lots[i])-1])
                        # Si la nouvelle prod égale l'ancienne alors nous metons à disposition les t-shirts en surplus du dernier lot
                        if prod_new == prod_lots[i] :
                            d[liste_lots[len(liste_lots[i])-1][k]] += prod_lots[len(liste_lots[i])-1] - prod_new

                        # Sinon nous mettons à jours chaque t-shirts du nouveau type de lots 
                        else : 
                            for l in range (len(liste_lots[i])):
                                d[liste_lots[i][l]] += prod_lots[i] - prod_new

                        # Mise à jour des productions de lots 
                        prod_lots[i] = prod_new
                          



def gain_interchanger(a, b, liste_a, prod_a, dispo_b) :
    # Calcul de l'ancien gain
    ancien_gain = gain_associé_interchangement(liste_a, prod_a)
    #print("erreur")
    #print(ancien_gain)

    # Calcul du nouveau gain

    # Nous retirons l'élément à enlever et ajoutons le nouveau
    liste_a.remove(a)
    liste_a.append(b)
    
    # Nous définissons la nouvelle production
    prod_new = min(dispo_b, prod_a)
    
    nouveau_gain = gain_associé_interchangement(liste_a, prod_new)


    if nouveau_gain - ancien_gain > 0 :
        return True
    else : 
        return False


def gain_associé_interchangement(liste_lots, prod_lots):
    res = 0
    for i in range (len(liste_lots)):
        res += (v[liste_lots[i]])*prod_lots
    return int(res)

# Inverse deux éléments
def inverse_deux_éléments (a, b, liste):
    liste.remove(a)
    liste.append(b)
    return liste