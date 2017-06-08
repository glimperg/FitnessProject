
## Data
Bij deze app wordt gebruikt gemaakt van een Firebase database.
Deze database bevat verschillende oefeningen en hun eigenschappen, zoals:
* Beschrijving van de oefening
* Benodigdheden
* Spiergroepen die getraind worden
* Afbeeldingen

Verder worden alle gemaakte workouts in de database opgeslagen per gebruiker,
net als de inhoud van de planner.

## Classes
Er wordt gebruik gemaakt van verschillende classes:
* Een class voor elke activity/fragment
* Verschillende Adapters voor de layout van activities
* Een Exercise en een Workout object class

## Schema
Het volgende schema geeft het ontwerp van de verschillende activities weer.
![](doc/design.jpg)

De afbeelding hieronder geeft schematisch de relaties tussen
de verschillende activities weer.
![](doc/design_small.jpg)
