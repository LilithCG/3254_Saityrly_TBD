package models

import (
	"fmt"
	"math/rand"
	"time"
)

const (
	HardFight  int = 40
	EqualFight int = 20
	EasyFight  int = 5
)

type Player struct {
	hole_length int
	health      int
	respect     int
	weight      int
}

func NewPlayer() *Player {
	return &Player{
		hole_length: 10,
		health:      100,
		respect:     20,
		weight:      30,
	}
}

func (p *Player) DigHole(intensively bool) {
	if intensively {
		p.hole_length += 5
		p.health -= 30
	} else {
		p.hole_length += 2
		p.health -= 10
	}
}

func (p *Player) EatGrass(fresh bool) {
	if !fresh {
		p.health += 10
		p.weight += 15
	} else if p.respect < 30 {
		p.health -= 30
	} else {
		p.health += 30
		p.weight += 30
	}
}

func (p *Player) Sleep() {
	p.hole_length -= 2
	p.health += 20
	p.respect -= 2
	p.weight -= 5
}

// Тесты на этот метод не писал, из-за рандома
func (p *Player) Fight(m Monster) {
	winChance := p.weight / (int(m) + p.weight)
	s := rand.NewSource(time.Now().Unix())
	r := rand.New(s)
	realChance := r.Float32()

	switch {
	case float32(winChance) > 0.5:
		if realChance >= float32(winChance) {
			p.respect += EasyFight
		} else {
			p.health -= EasyFight
		}
	case float32(winChance) == 0.5:
		if realChance >= float32(winChance) {
			p.respect += EqualFight
		} else {
			p.health -= EqualFight
		}
	default:
		if realChance >= float32(winChance) {
			p.respect += HardFight
		} else {
			p.health -= HardFight
		}
	}
}

func (p *Player) IsLose() bool {
	return p.health <= 0 || p.hole_length <= 0 || p.respect <= 0 || p.weight <= 0
}

func (p *Player) IsWin() bool {
	return p.respect >= 100
}

func (p *Player) PrintCharacters() string {
	return fmt.Sprintf("Your characters:\n\tHole length: %v\n\tHealth: %v\n\tWeight: %v\n\tRespect: %v\n\n",
		p.hole_length,
		p.health,
		p.weight,
		p.respect,
	)
}
