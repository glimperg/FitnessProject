# Week 1

## Dag 2, 6-6-17

* Gezocht naar een goede API met oefeningen. https://wger.de/api/v2 lijkt
goed te zijn, maar de API is niet precies hoe ik hem wil hebben: weinig plaatjes
en soms geen goede uitleg van de oefening. Ik ga hier nog over nadenken.
* *Project Proposal* geschreven en nagedacht over de precieze inhoud van de app.
* Schetsen van verschillende onderdelen van de app gemaakt:

![](doc/exercises.jpg)

![](doc/workouts.jpg)

![](doc/schedule.jpg)

## Dag 3, 7-6-17

* Stand-ups gedaan samen met studiegenoten. Ze vonden de app een leuk idee
en gaven goede feedback.
* Begin gemaakt aan het *Design Document* en het prototype.
* Ik heb de API van *wger* gebruikt om een Firebase database te maken, zodat de database
precies de structuur heeft die ik nodig heb. Veel werk en veel tekst herschreven.

## Dag 4, 8-6-17

* Bij de stand-ups is opnieuw goede feedback gegeven. Ik had nog niet goed
nagedacht over hoe ik precies de workouts van een gebruiker wilde opslaan.
Ik heb besloten dit in Firebase te zetten, en dus ook gebruik te maken van
de Firebase authenticatie.
* LoginActivity geïmplementeerd met behulp van Firebase authenticatie.
* Exercise en Workout classes aangemaakt. De Exercise class haalt een
JSONObject op uit de Firebase database en zet het om in een bruikbaar object.
Verder bestaat de Workout class uit verschillende Exercise objects.
Het lijkt me makkelijker om classes te gebruiken dan alleen met JSONObjects
te werken. Aan de ene kant kost het eerst meer tijd om de JSONObjects om te zetten
in Exercise classes. Toch blijkt dat zulke classes vaak handiger zijn om
mee te werken (zo hoef je geen rekening meer te houden met JSONExceptions).
* Aantal kleine dingen aan het prototype gedaan.
* Nieuwe visualisatie van de app gemaakt:
![](doc/design.jpg)
Verder nog een schema met de relaties tussen activities:
![](doc/design_small.jpg)

## Dag 5, 9-6-17

* Presentatie gehad. Het idee voor de app lijkt te veel op vergelijkbare apps.
Er moet dus nog iets worden toegevoegd. Misschien achievements/een andere beloning
na het voltooien van workouts. Ik ga hier dit weekend nog over nadenken.
* Opnieuw de Firebase database aangepast. De oefeningen zijn nu in een apart
JSONObject per categorie. Dit werkt beter, want in de app moeten de oefeningen
ook gesorteerd op categorie.
* CustomExpandableListAdapter gemaakt voor de DatabaseFragment. Ik heb voor een
ExpandableListView gekozen, aangezien hierdoor elke oefening bij
een bepaalde categorie is geplaatst.
Dit maakt de lijst oefeningen een stuk overzichtelijker.
* Begin gemaakt aan DatabaseFragment. Alle data wordt opgehaald uit Firebase,
alleen er zijn nog enkele bugs aanwezig.

# Week 2

## Dag 1, 12-6-17

* Problemen bij DatabaseFragment opgelost en verder DatabaseFragment afgemaakt door een Intent en onChildClickListener toe te voegen.
* ImageAsyncTask gemaakt om afbeeldingen op te kunnen halen. Het was lastig om de URL mee te geven aan de ImageView bij het intialiseren van een nieuwe ImageAsyncTask, maar dit is gelukt door imageView.setTag() te gebruiken.
* ExerciseActivity gemaakt en het layout-bestand ingericht. Ik heb bij deze layout gekozen voor een ScrollView, aangezien anders de tekst van het scherm zou kunnen gaan.
* Ik moet nog nadenken over de grootte en schaal van de afbeeldingen bij de oefeningen. Sommige afbeeldingen zijn namelijk heel breed en andere zijn vooral in de lengte groot. Ik heb nu het minimum van een vaste waarde en (een factor van) de grootte van de afbeelding gekozen, maar enkele zijn nog niet perfect.

## Dag 2, 13-6-17

* Bij de stand-ups de voortgang van de vorige dag besproken. Julia kwam met een goed idee over het uniek maken van de app: namelijk het toevoegen van sjablonen voor workouts, die de gebruiker kunnen helpen bij het maken van een goede workout. Hierdoor wordt voorkomen dat de gebruiker niet enkel dezelfde spiergroep traint, want dit is niet goed.
