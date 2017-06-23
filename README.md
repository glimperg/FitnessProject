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
Verder kan het lastig zijn om een goede planning met workouts te maken.

*Work Out* probeert de gebruiker inzicht te geven in de mogelijkheden
van een goede workout. Verder heeft de app het doel om (beginnende)
sporters zo veel mogelijk te helpen bij het trainen.

## Kenmerken
De gebruiker kan informatie vinden over allerlei oefeningen
en aan de hand van deze oefeningen zelf een workout samenstellen.
Hierbij kan gebruik worden gemaakt van een sjabloon die de gebruiker helpt bij het kiezen van de verhouding van soorten oefeningen.
Ten slotte kunnen deze persoonlijke workouts worden geplaatst in een planner.
Bij deze planner kan de gebruiker per dag een activiteit selecteren. Verder kunnen deze activiteiten worden afgevinkt na voltooiing.

## Technisch
Er zijn drie verschillende onderdelen van *Work Out*.
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
De gebruiker kan dan bijvoorbeeld aangeven de buikspieren te willen trainen, waarna er een sjabloon voor een buikspierworkout verschijnt. Er kan dan om en om een buikspier- en een rugspieroefening worden gekozen.

Daarnaast kan de gebruiker een trainingsschema aanmaken.
Elke dag van de week kan worden opgevuld met de gecreërde workouts, hardlopen, fietsen of een rustdag. Verder kunnen deze activiteiten worden aangevinkt bij voltooiing en wordt er bijgehouden hoeveel workouts er zijn voltooid.

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
