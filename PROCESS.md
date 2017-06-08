# Dag 1, 6-6-17

* Gezocht naar een goede API met oefeningen. https://wger.de/api/v2 lijkt
goed te zijn, maar de API is niet precies hoe ik hem wil hebben: weinig plaatjes
en soms geen goede uitleg van de oefening. Ik ga hier nog over nadenken.
* *Project Proposal* geschreven en nagedacht over de precieze inhoud van de app.
* Schetsen van verschillende onderdelen van de app gemaakt:

![](doc/exercises.jpg)

![](doc/workouts.jpg)

![](doc/schedule.jpg)

# Dag 2, 7-6-17

* Stand-ups gedaan samen met studiegenoten. Ze vonden de app een leuk idee
en gaven goede feedback.
* Begin gemaakt aan het *Design Document* en het prototype.
* Ik heb de API van *wger* gebruikt om een Firebase database te maken, zodat de database
precies de structuur heeft die ik nodig heb. Veel werk en veel tekst herschreven.

# Dag 3, 8-6-17

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
