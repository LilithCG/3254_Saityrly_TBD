package game

import (
	"fmt"
	"io"
	"os"
	"time"

	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/console_game/models"
)

type Actions int

const (
	Dig   Actions = 1
	Eat   Actions = 2
	Sleep Actions = 3
	Fight Actions = 4
)

type Game struct {
	p *models.Player
	r io.Reader
	w io.Writer
}

func NewGame(p *models.Player, r io.Reader, w io.Writer) *Game {
	return &Game{
		p: p,
		r: r,
		w: w,
	}
}

func (g Game) setDay(a Actions, opts ...bool) {
	switch a {
	case Dig:
		g.p.DigHole(opts[0])
	case Eat:
		g.p.EatGrass(opts[0])
	case Sleep:
		g.p.Sleep()
	case Fight:
		fmt.Fprint(g.w, "Please choose boss level:\n\t1: Easy\n\t2: Medium\n\t3: Hard\n\n")
		var m models.Monster
		var i int
		for {
			if _, err := fmt.Fscan(g.r, &i); err != nil || i < 0 || i > 3 {
				fmt.Fprint(g.w, "Something wrong!\nPlease, try again...\n\n")
			} else {
				break
			}
		}
		switch i {
		case 1:
			m = models.Easy
		case 2:
			m = models.Medium
		case 3:
			m = models.Hard
		}
		g.p.Fight(m)

	default:
		fmt.Fprint(g.w, "Error command!")
	}
}

func (g Game) setNight() {
	g.p.Sleep()
}

func (g Game) Game() {
	var i Actions
	var b int

	fmt.Fprint(g.w, "Welcome to game...\n\n")
	fmt.Fprint(g.w, "Enter some key to continue...\n")
	fmt.Scanln()

	for {
		fmt.Fprint(g.w, "Good Morning!\nPlease, choose what you want to do:\n1. Dig\n2. Eat\n3. Sleep\n4. Fight\n\n")
		for {
			if _, err := fmt.Fscanln(g.r, &i); err != nil || i > 4 || i < 1 {
				fmt.Fprint(g.w, "Something wrong!\nPlease, try again...\n\n")
			} else {
				break
			}
		}
		time.Sleep(1 * time.Second)
		if i == Dig || i == Eat {
			if i == Dig {
				fmt.Fprint(g.w, "Please enter:\n\t1. If you need do this intensively\n\t2. If otherwise\n\n")
			} else {
				fmt.Fprint(g.w, "Please enter:\n\t1. If you want eat fresh\n\t2. If otherwise\n\n")
			}

			for {
				if _, err := fmt.Fscanln(g.r, &b); err != nil || b < 1 || b > 2 {
					fmt.Fprint(g.w, "Something wrong!\nPlease, try again...\n\n")
				} else {
					break
				}
			}

			g.setDay(i, b == 1)
		} else {
			g.setDay(i)
		}
		time.Sleep(1 * time.Second)
		fmt.Fprintln(g.w, g.p.PrintCharacters()+"\n")

		fmt.Fprint(g.w, "Enter some key to continue...\n")
		fmt.Scanln()
		time.Sleep(1 * time.Second)

		if g.p.IsLose() {
			fmt.Fprint(g.w, "You lose...\n\n")
			break
		} else if g.p.IsWin() {
			fmt.Fprint(g.w, "You win!!!\n\n")
			break
		}

		fmt.Fprint(g.w, "Good night!\nI hope you last one more night...\n\n")
		g.setNight()
		time.Sleep(1 * time.Second)
		fmt.Fprintln(g.w, g.p.PrintCharacters())
		fmt.Fprint(g.w, "Enter some key to continue...\n")
		fmt.Scanln()
		time.Sleep(1 * time.Second)

		if g.p.IsLose() {
			fmt.Fprint(g.w, "You lose...\n\n")
			break
		}
	}
	fmt.Fprint(g.w, "Thank you for this game...\n\n")
	fmt.Fprint(g.w, "Enter some key to exit...\n")
	fmt.Scanln()

	os.Exit(0)
}
