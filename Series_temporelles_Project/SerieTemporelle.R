###############################################
#Projet M2 Statistique
#Series Chronologiques-Donnees de survie
###############################################
#Chen HUANG et Clara LAMBERT
###############################################
#Sujet 7:On demande d'??tudier la s??rie suivante qui indique 
#le nombre d'immatriculation mensuelles de camions 
#de plus de 27 tonnes de 1968 ?? 1977
##################################################
#R??SUM??
#L???objectif du projet suivant est de pr??dire une s??rie temporelle. A cette fin, des diff??rentes m??thodes ont ??t?? exploit??es, seules celles ayant les meilleures qualit??s pr??dictives ont ??t?? retenues.


#install.packages("forecast")
#install.packages("stringr")
#install.packages("caschrono")
#install.packages("TSA")
#install.packages("TTR")

library(tidyr)
library(tseries)
library(forecast)
library(caschrono)
library(TSA)
library(TTR)
library(zoo)

# donne??s observ??e
Immatriculation_Camion<-read.table("Immatriculation_Camion_Mensuelle.txt",head=TRUE,sep=",")

Immatriculation_Camion<-Immatriculation_Camion[,-1]
Immatriculation_Camion
Immatriculation_Camion_ts<-ts(Immatriculation_Camion,frequency = 12,start=c(1968,1))
Immatriculation_Camion_ts
# Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec
#1968 317 323 383 374 254 280 390 352 239 363 347 309
#1969 453 411 445 447 470 416 439 345 357 467 468 441
#1970 508 427 487 616 443 530 444 265 370 416 430 370
#1971 559 419 559 535 501 549 471 312 371 430 448 423
#1972 491 473 525 589 630 703 577 404 417 592 649 509
#1973 634 660 920 734 773 702 675 538 573 794 801 572
#1974 915 863 893 901 924 776 713 461 476 526 408 328
#1975 395 358 329 362 333 404 439 278 367 645 404 354
#1976 561 556 739 696 656 761 785 396 515 613 596 518
#1977 802 

#graphique
plot.ts(Immatriculation_Camion_ts,main="Immatriculation Camion")
logImmatriculation_Camion_ts<-log(Immatriculation_Camion_ts)
plot.ts(logImmatriculation_Camion_ts)

#graphique avec ACF et PACF
tsdisplay(Immatriculation_Camion_ts)

#Decomposition
Immatriculation_Camion_ts_components<-decompose(Immatriculation_Camion_ts)
plot(Immatriculation_Camion_ts_components)

# differencier 12 fois
Immatriculation_Camion_ts_diff12<-diff(Immatriculation_Camion_ts,12)
#graphique avec ACF et PACF apres differencier 12 fois
tsdisplay(Immatriculation_Camion_ts_diff12)

acf(Immatriculation_Camion_ts_diff12,lag.max=24,main="correlogramme de (1-B^12)Xt",ci.type="ma")

pacf(Immatriculation_Camion_ts_diff12,lag.max=36,main="correlogramme partiel de (1-B^12)Xt")
plot(decompose(Immatriculation_Camion_ts_diff12))

# differencier encore une fois
Immatriculation_Camion_ts_diffdiff12<-diff(Immatriculation_Camion_ts_diff12,lag=1,difference=1)
Immatriculation_Camion_ts_diffdiff12
tsdisplay(Immatriculation_Camion_ts_diffdiff12)
acf(Immatriculation_Camion_ts_diffdiff12,lag.max=36,main="correlogramme de (1-B)(1-B^12)Xt",ci.type="ma")
pacf(Immatriculation_Camion_ts_diffdiff12,lag.max=36,main="correlogramme partiel de (1-B)(1-B^12)Xt")
plot(decompose(Immatriculation_Camion_ts_diffdiff12))

#model1
model1<-arima(Immatriculation_Camion_ts,order=c(0,1,1),seasonal=list(order=c(0,1,1),periode=12))
model1   #AIC=1136.87
t_stat(model1)
Box.test(residuals(model1),type="Ljung-Box")
plot(residuals(model1))
acf(residuals(model1),lag.max=36, main="correlogramme de residuals(model1)",ci.type="ma")

#model2
model2<-arima(Immatriculation_Camion_ts,order=c(1,1,1),seasonal=list(order=c(0,1,1),periode=12))
model2  #AIC=1138.81
t_stat(model2)
Box.test(residuals(model2),type="Ljung-Box")

#model3
model3<-arima(Immatriculation_Camion_ts,order=c(1,0,1),seasonal=list(order=c(0,1,1),periode=12))
model3 #AIC=1148.51
t_stat(model3)
Box.test(residuals(model3),type="Ljung-Box")

# prediction avec 2 methode
#1 prevision avec forecast 
Immatriculation_Camion_model3_forecasts<-forecast(Immatriculation_Camion_ts,h=11)
plot(Immatriculation_Camion_model3_forecasts)

acf(Immatriculation_Camion_model3_forecasts$residuals,lag.max=20, main="correlogramme de residuals(model3)",ci.type="ma")
Box.test(Immatriculation_Camion_model3_forecasts$residuals,lag=20,type="Ljung-Box")

plot.ts(Immatriculation_Camion_model3_forecasts$residuals)
mean(Immatriculation_Camion_model3_forecasts$residuals)

# prediction
pred=predict(model3,12)
pred


#tendance
#apr??s plusieurs essayage on trouve avec moyenne mobile n=12 est la meilleure
Immatriculation_Camion_ts_SMA12<-SMA(Immatriculation_Camion_ts,n=12)
plot.ts(Immatriculation_Camion_ts_SMA12)

#obtenir les valeur estim??e de la partie saisionalit??
Immatriculation_Camion_ts_components$seasonal
Immatriculation_Camion_ts_saisionalite_modifie<-Immatriculation_Camion_ts-Immatriculation_Camion_ts_components$seasonal
plot(Immatriculation_Camion_ts_saisionalite_modifie)

#2prevision avec methode HoltWinters
Immatriculation_Camion_forecasts<-HoltWinters(Immatriculation_Camion_ts,beta=FALSE,gamma=FALSE,l.start=317,b.start=6)
Immatriculation_Camion_forecasts

Immatriculation_Camion_forecasts$fitted
plot(Immatriculation_Camion_forecasts)

Immatriculation_Camion_forecasts$SSE #somme des carr??s des erreurs est 1312802

Immatriculation_Camion_forecasts2<-forecast(Immatriculation_Camion_forecasts, h=11)
Immatriculation_Camion_forecasts2
plot(forecast(Immatriculation_Camion_forecasts2)) #bleu 80% gris 95% invervall de prediction

acf(Immatriculation_Camion_forecasts2$residuals,lag.max=20,na.action = na.pass)
Box.test(Immatriculation_Camion_forecasts2$residuals,lag=20,type="Ljung-Box")
plot.ts(Immatriculation_Camion_forecasts2$residuals)

#Donc notre model 3 est la meilleur qui est ARIMA(1,0,1)(0,1,1)[12]

