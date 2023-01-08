package main

import (
	"os"

	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/console_game/game"
	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/console_game/models"
)

func main() {
	p := models.NewPlayer()
	g := game.NewGame(p, os.Stdin, os.Stdout)

	g.Game()
}
