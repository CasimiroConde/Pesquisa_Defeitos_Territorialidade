mydata = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/Repositorios.csv")

datafilter = subset(mydata, Nissue != 0)

############RQ1

#########Idade(Semana) X numero de Issues
wilcox.test(mydata$IdadeSemana, mydata$Nissue, correct=FALSE)

#BoxPlot
boxplot(IdadeSemana ~ ContemIssue, data=mydata, main="Semana X Issues",
		xlab= "Issues", ylab="Semana",ylim=c(350,450))

#########Tamanho X numero de Issues
wilcox.test(mydata$KB, mydata$Nissue, correct=FALSE)

#BoxPlot
boxplot(KB ~ ContemIssue, data=mydata, main="Kilobites X Issues",
		xlab= "Issues", ylab="Kilobytes",log="y")

#########Tamanho Equipe X numero de Issues
wilcox.test(mydata$Contributors, mydata$NIssues, correct=FALSE)

#BoxPlot
boxplot(ContributorsAjustado ~ ContemIssue, data=mydata, main="ContributorsX Issues",
		xlab= "Issues", ylab="Contributors", log= "y")

#########Numero de Followers X numero de Issues
wilcox.test(mydata$Followers, mydata$Nissue, correct=FALSE)

#BoxPlot
boxplot(FollowersAjustado ~ ContemIssue, data=mydata, main="Followers X Issues",
		xlab= "Issues", ylab="Followers", log = "y")


############RQ2

#Distribuição de Issues
mydataissue = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/DistribuicaoIssue/Distribuicao.csv")
mydataissue

#Number Issues / MegaByte
mean(mydata$IssueXMB)
boxplot(mydata$IssueXMBAjustado, horizontal=TRUE)

#Correlacao Spearman
cor.test(mydata$MB, mydata$Nissue, method="spearman")

#Scatterplot Kilobytes X NIssues
plot(Nissue ~ MB, data=mydata, log="xy")
abline(lm(mydata$Nissue ~ mydata$MB))

############RQ3

#Totalização
mydatatotalizacao = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/TotalizacaoIssueLabel/TotalizacaoIssueLabel.csv")
mydatatotalizacao

#Distribuição de Labels
mydatalabel = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/DistribuicaoLabel/DistribuicaoLabel.csv")
mydatalabel

#Distribuicao Linguagem
mydatalinguagem = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/LinguagemIssue/LinguagemIssueTop10.csv")
mydatalinguagem

mydatatop10 = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/LinguagemIssue/RepositoriosTop10.csv")

boxplot(NissueAjustado ~ Linguagem, data=mydatatop10, las = 1, 
		xlab= "Issue", log="x", horizontal=TRUE)


##Issues Reporters Por Linguagem

############RQ4
mydatacontributorissue = read.csv2("C:/Users/Casimiro/git/Territorialidade/arquivos/Arquivos utilização R/07112015/AnaliseReporters/ReportersDevelopers	.csv")
mydatacontributorissue

boxplot(Envolvidos ~ ReporterQueDesenvolverTotal + DesenvolvedorQueReportaTotal, data=mydatacontributorissue , main="Contributors")

############RQ5

##Watchers X Issues 
##Correlacao Spearman
cor.test(mydata$Watchers, mydata$Nissue, method="spearman")

##Scatterplot Watchers X NIssues
plot(Nissue ~ Watchers, data=mydata,  log="xy")
abline(lm(mydata$Nissue ~ mydata$Watchers))


##Watchers X Issues Reporters
##Correlacao Spearman
cor.test(mydata$Watchers, mydata$ReportersIssue, method="spearman")

##Scatterplot Watchers X ReportersIssue
plot(ReportersIssue ~ Watchers, data=mydata, log="xy")
abline(lm(mydata$ReportersIssue ~ mydata$Watchers))

##Forks X Issues 
##Correlacao Spearman
cor.test(mydata$Forks, mydata$Nissue, method="spearman")

##Scatterplot Forks X Issue
plot(Nissue ~ Forks, data=mydata, log="xy")
abline(lm(mydata$ReportersIssue ~ mydata$Forks))


##Forks X Issues Reporters
##Correlacao Spearman
cor.test(mydata$Forks, mydata$ReportersIssue, method="spearman")

##Scatterplot Forks X ReportersIssue
plot(ReportersIssue ~ Forks, data=mydata, log="xy")
abline(lm(mydata$ReportersIssue ~ mydata$Forks))


############RQ6

##Numero de Issue Reporters X Time-to-fix

##Time to fix X Reporters Issues
##Correlacao Spearman
cor.test(mydata$ReportersIssue, mydata$MediaTimeToFix, method="spearman")

##Scatterplot Watchers X NIssues
plot(ReportersIssue ~ MediaTimeToFix, data=mydata,log="xy")
abline(lm(mydata$Reporters ~mydata$MediaTimeToFix))