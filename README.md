# Work Out
*Gido Limperg, 11060727*

## Probleem
Er is tegenwoordig steeds meer een drang bij mensen om gezonder te leven.
Regelmatig sporten is hier een belangrijk onderdeel van.
Het is echter voor de gemiddelde persoon erg lastig om erachter te komen
hoe je het beste aan de slag kunt gaan met fitness.
Een gebalanceerde workout is essentieel om zo veel mogelijk voortgang te boeken,
maar veel mensen weten niet precies welke oefeningen het beste bij elkaar passen
en trainen daardoor bijvoorbeeld enkel hun buikspieren of biceps.
<!--Verder kan het lastig zijn om een trainingsschema te maken om bijvoorbeeld
naar een bepaald doel toe te werken.!-->

*Work Out* probeert de gebruiker inzicht te geven in de mogelijkheden
van een goede workout. Verder heeft de app het doel om (beginnende)
sporters zo veel mogelijk te helpen bij het behalen van hun einddoel.

## Kenmerken
De gebruiker kan informatie vinden over allerlei oefeningen
en aan de hand van deze oefeningen zelf een workout samenstellen.
Hierbij kan gebruik worden gemaakt van een sjabloon die de gebruiker helpt bij het kiezen van het juiste aantal en de verhoudingen van soorten oefeningen.
Vervolgens kunnen deze persoonlijke workouts worden geplaatst in een planner.
Bij deze planner kan de gebruiker per dag een activiteit selecteren.
Ten slotte kan de gebruiker tijdens de workout de app gebruiken om
bij te houden hoe ver hij op dat moment met zijn workout is.
Zo kan na het voltooien van een oefening op een knop worden gedrukt
waarna een korte rustpauze wordt ingelast, om vervolgens verder te gaan
met de volgende oefening.

## Technisch
Er zijn vijf verschillende onderdelen van *Work Out*.
Ten eerste is er een uitgebreide database met allerlei oefeningen.
Er kan specifiek naar een oefening worden gezocht op basis van naam,
spiergroep, benodigdheden, etc.
Elke oefening bevat onder andere een beschrijving,
de benodigde apparatuur, belangrijke spiergroepen en eventueel
een afbeelding van de uitvoering.

Verder is het mogelijk om met behulp van deze oefeningen
zelf een workout samen te stellen. Hierbij wordt telkens een oefening en
een bepaald aantal herhalingen gekozen.
Deze workout kan zelf worden samengesteld, maar ook met behulp van een soort sjabloon.
De gebruiker kan dan bijvoorbeeld aangeven de buikspieren te willen trainen, waarna er een sjabloon voor een workout van vijf oefeningen verschijnt. Er kan dan om en om een buikspier- en een rugspieroefening worden gekozen.

Daarnaast kan de gebruiker een trainingsschema aanmaken.
Elke dag van de week kan worden opgevuld met vooraf gecreëerde workouts,
persoonlijke workouts en cardio (hardlopen of fietsen).

Ook is er de mogelijkheid om tijdens de workout de voortgang bij te houden.
Na elke opdracht drukt de gebruiker op een knop om aan te geven dat hij klaar is.
Bij opdrachten van een vaste tijdsduur loopt in plaats daarvan een timer.
Vervolgens loopt er een timer voor een korte rustpauze,
waarna de volgende opdracht begint.

Ten slotte is er een profielpagina van de gebruiker. Deze pagina laat bijvoorbeeld zien hoeveel workouts de gebruiker heeft voltooid. Hier is eventueel ook enige aanpassing van de app mogelijk (bv. verschillende kleurenthema's die na een bepaald aantal workouts worden ontgrendeld).

## Visualisatie

![](doc/design.jpg)


## Externe middelen
Bij deze app wordt gebruik gemaakt van Firebase authenticatie voor het kunnen
aanmaken van accounts en een Firebase database.
Deze database bevat alle oefeningen en hun bijbehorende informatie.
Dit onderdeel van de database is gemaakt met behulp van de *wger Workout Manager* API:
https://wger.de/api/v2.
Verder bevat de database per gebruiker een lijst met de persoonlijke workouts en planner.

## Mogelijke problemen
Er zijn een aantal technische problemen die tijdens het maken van de app kunnen verschijnen.
Ten eerste heb ik bij het maken van een vorige app ervaren dat de Firebase database die bij dit project wordt gebruikt, niet altijd goed werkt. Het kan bijvoorbeeld lastig zijn om data op een bepaalde manier op te halen.
Verder lijkt het erg moeilijk om workouts te implementeren, zo dat je deze met behulp van de app kunt uitvoeren. Het scherm moet namelijk telkens wijzigen en er zijn verschillende soorten oefeningen (op basis van aantal herhalingen, of op basis van tijd).
Ten slotte kan het zijn dat de geplande app te veel of juist te weinig werk is, maar dit is op dit moment nog lastig in te schatten.

## Vergelijkbare apps
Een voorbeeld van een vergelijkbare app is *Workout Tracker & Gym Trainer*.
Deze app bevat een grote database met oefeningen en het is ook mogelijk
om zelf workouts te maken. De mogelijkheid om een trainingsschema te maken
is echter niet aanwezig.

## *Minimum Viable Product*
Het belangrijkste onderdeel van de app is het kunnen opzoeken van verschillende
oefeningen en hun uitvoering in de database. Daarnaast bestaat het
*Minimum Viable Product* uit het maken van workouts en een weekplanner.
Optionele toevoegingen de mogelijkheid om tijdens een workout de voortgang van de workout bij te houden en een profielpagina met statistieken en evt. beloningen.

[![BCH compliance](https://bettercodehub.com/edge/badge/glimperg/Work-Out?branch=master)](https://bettercodehub.com/)
