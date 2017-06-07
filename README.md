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
Verder kan het lastig zijn om een trainingsschema te maken om bijvoorbeeld
naar een bepaald doel toe te werken.

*Work Out* probeert de gebruiker inzicht te geven in de mogelijkheden
van een gebalanceerde workout. Verder heeft de app het doel om (beginnende)
sporters zo veel mogelijk te helpen bij het behalen van hun einddoel.

## Kenmerken
De gebruiker kan informatie vinden over allerlei oefeningen
en aan de hand van deze oefeningen zelf een workout samenstellen.
Vervolgens kunnen deze persoonlijke workouts worden geplaatst in een trainingsschema
voor een vast aantal weken.
Bij zo'n schema kan de gebruiker een doel stellen (bv. 100 push-ups of 10 km hardlopen)
en aan de hand van dit doel de intensiteit van de workouts geleidelijk laten oplopen.
Ten slotte kan de gebruiker tijdens de workout de app gebruiken om
bij te houden hoe ver hij op dat moment met zijn workout is.
Zo kan na het voltooien van een oefening op een knop worden gedrukt
waarna een korte rustpauze wordt ingelast, om vervolgens verder te gaan
met de volgende oefening.

## Technisch
Er zijn vier verschillende onderdelen van *Work Out*.
Ten eerste is er een uitgebreide database met allerlei oefeningen.
Er kan specifiek naar een oefening worden gezocht op basis van naam,
spiergroep, benodigdheden, etc.
Elke oefening bevat onder andere een beschrijving,
de benodigde apparatuur, belangrijke spiergroepen en eventueel
een afbeelding van de uitvoering.

Verder is het mogelijk om met behulp van deze oefeningen
zelf een workout samen te stellen. Hierbij wordt ook aandacht besteed
aan de verhoudingen van getrainde spiergroepen. Zo is het bijvoorbeeld
verstandig om een buikspieroefening aan te vullen met een oefening voor de rugspieren.

Daarnaast kan de gebruiker een trainingsschema aanmaken. Er wordt een bepaalde
duur van het schema gekozen en eventueel een einddoel gekozen.
Het schema kan vervolgens worden opgevuld met vooraf gecreëerde workouts,
persoonlijke workouts en cardio (hardlopen, fietsen of zwemmen).
Hierbij kan de gebruiker ervoor kiezen om per week de intensiteit van
het programma op te laten lopen.

Ten slotte is er de mogelijkheid om tijdens de workout de voortgang bij te houden.
Na elke opdracht drukt de gebruiker op een knop om aan te geven dat hij klaar is.
Bij opdrachten van een vaste tijdsduur loopt in plaats daarvan een timer.
Vervolgens loopt er een timer voor een korte rustpauze,
waarna de volgende opdracht begint.

## Visualisatie

![alt text](doc/exercises.jpg "Database van oefeningen")

![alt text](doc/workouts.jpg "Samengestelde workout")

![alt text](doc/schedule.jpg "Trainingsschema")


## Externe middelen
Bij deze app wordt gebruik gemaakt van de *wger Workout Manager* API:
https://wger.de/api/v2. Met behulp van deze API wordt de database met oefeningen gemaakt.

## Limitaties
Er zijn een aantal technische problemen die tijdens het maken van de app kunnen verschijnen.
Ten eerste kan het zijn dat de *wger* API niet precies werkt zoals verwacht.
Een mogelijkheid om dit op te lossen is het vinden van een andere API
of eventueel door zelf een database met oefeningen te maken.
Verder is het mogelijk dat sommige onderdelen van de app verrassend moeilijk
zijn om te implementeren. In dit geval is het een optie om dit onderdeel aan
te passen of wellicht helemaal te schrappen.

## Vergelijkbare apps
Een voorbeeld van een vergelijkbare app is *Workout Tracker & Gym Trainer*.
Deze app bevat een grote database met oefeningen en het is ook mogelijk
om zelf workouts te maken. De mogelijkheid om een trainingsschema te maken
is echter niet aanwezig.

## *Minimum Viable Product*
Het belangrijkste onderdeel van de app is het kunnen opzoeken van verschillende
oefeningen en hun uitvoering in de database. Daarnaast bestaat het
*Minimum Viable Product* uit het maken van trainingsschema's.
Een optionele toevoeging is de mogelijkheid om tijdens
een workout de voortgang van de workout bij te houden.
