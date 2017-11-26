# XenionTD

## Introduction

Xenion TD is the final result of my Android craftmanship. The main goal is to build tower, upgrade them, let them aim & kill monster and collect money.

It's worth mentioning that a particular attention to the UI has been taken :
- A bar showing the number of monster present to the field
- a swipable menu that show your score / money / lives ( swipe left to open, right to close )
- a health indicator located top left corner
- a range indicator displayed whenever you click on any tower
- different board for different season ( green for sprint / summer, red for autumn, blue for winter )

You'll find 4 screenshot in the folder root.
It's a project that I plan to eventually release on the Google Store.

## Contributor

This project was developped with the help of Omar Bouach

## TODO

*Logical*
- Improve even more the way entities communicate together ( more semantic & human readable )
- Reduce the overall projectile size, and make it configurable ( scaling upon a defined constant )

*Bug*
- Refund doesn't actually refund
- Fix the projectile trajectory so it always aim at the target's center regardless of the direction
where it was fired

*Feature&
- Add the possibility to accelerate x2 / x5 the game speed
- Add the possibility to slow x2 / x5 the game speed
- Add the possibility to pause the game
- Add the possibility to save / load a game
- Add diifferent effect on tower ( only freezing effect for now )
- Add different effect on ennemies ( tank / fast walker / boss )
- Add different spell for the user to cast

*UI*
- Enhanced welcome / splash screen
- Add a visual element to explain that you can swipe left to display an info panel

*Final*
- Animation on hit ( depending of the tower's effect )
- Animation on tower build / refund
- Animation on ground loading

