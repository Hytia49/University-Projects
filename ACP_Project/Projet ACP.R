
villes<-read.table("Sujet4.txt",header=T)
villes

dim(villes)

summary(villes)

villes1<-villes[,1:10]
mcor<-cor(villes1)
mcor

install.packages("corrplot")
library(corrplot)
corrplot(mcor,type="upper",order="hclust",tl.col="black",tl.srt=45)

library(FactoMineR)
res.pca=PCA(villes[,1:10],scale.unit=TRUE,ncp=5,graph=T)
###On n'utilise pas la variable logement car elle est trop loin du cercle unité
###mortalié et prixDuM2 s*ont diamétralement opposés, ils sont fortement corrélés négativement
###étudiants, cinéma et attirance sont proche, ils sont fortement corrélés positivement 
###presque une orthogonalité entre cinémas et criminalité, donc pas de corrélation
summary(res.pca)

###on peut dire que plus il y a de chômage, moins il y a d'impôts
res.pca <-PCA(villes, quali.sup=11, scale.unit=TRUE, ncp=4, graph=T) 

plot.PCA(res.pca, axes=c(1, 2), choix="ind", habillage=11)
plot.PCA(res.pca, axes=c(1, 3), choix="ind", habillage=11)
plot.PCA(res.pca, axes=c(2, 3), choix="ind", habillage=11)


dimdesc(res.pca, axes=c(1,2))

res.pca
names(res.pca)
res.pca$eig
res.pca$ind
res.pca$var
res.pca$quali.sup

### versaille et calais sont opposé
###on ne selectionne que les individus les mieux représentés
x11( )
plot(res, cex=0.8, shadow=TRUE, habillage=11, select="cos2 0.7", invisible=c("quali"))

###on selectionne les 5 individus avec le plus de contribution sur les axes 1 et 2
plot(res, cex=0.8, shadow=TRUE, habillage=11, select="contrib 5", invisible=c("quali"))

###on selectionne les 5 variables qui contribuent le plus fortement à la formation des axes
plot(res, choix="var", shadow="TRUE", select="contrib 5", cex=0.9, cex.main=1.5)

###Camembert pour les régions
region<-c(31,21,50,10)
names(region)<-c("Nord","Sud","Ouest","Est")
pie(region, main = "Diagramme circulaire pour région", col = rainbow(length(region)))







