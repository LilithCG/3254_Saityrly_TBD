package models

import (
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestDigHole(t *testing.T) {
	p_test := NewPlayer()
	ans_p := &Player{
		hole_length: 15,
		health:      70,
		respect:     20,
		weight:      30,
	}
	// intensively
	p_test.DigHole(true)
	assert.Equal(t, ans_p, p_test, "Two objects must be the same")

	p_test = NewPlayer()
	ans_p = &Player{
		hole_length: 12,
		health:      90,
		respect:     20,
		weight:      30,
	}
	// not intensively
	p_test.DigHole(false)
	assert.Equal(t, ans_p, p_test, "Two objects must be the same")
}

func TestEatGrass(t *testing.T) {
	// TEST 1
	p_test := NewPlayer()
	ans_p := &Player{
		hole_length: 10,
		health:      70,
		respect:     20,
		weight:      30,
	}
	// fresh and respect < 30
	p_test.EatGrass(true)
	assert.Equal(t, ans_p, p_test, "Two objects must be the same")

	// TEST 2
	p_test = &Player{
		hole_length: 10,
		health:      100,
		respect:     40,
		weight:      30,
	}
	ans_p = &Player{
		hole_length: 10,
		health:      110,
		respect:     40,
		weight:      45,
	}
	// not fresh
	p_test.EatGrass(false)
	assert.Equal(t, ans_p, p_test, "Two objects must be the same")

	// TEST 3
	p_test = &Player{
		hole_length: 10,
		health:      100,
		respect:     60,
		weight:      30,
	}
	ans_p = &Player{
		hole_length: 10,
		health:      130,
		respect:     60,
		weight:      60,
	}
	// fresh
	p_test.EatGrass(true)
	assert.Equal(t, ans_p, p_test, "Two objects must be the same")
}

func TestSleep(t *testing.T) {
	p_test := NewPlayer()
	ans_p := &Player{
		hole_length: 8,
		health:      120,
		respect:     18,
		weight:      25,
	}

	p_test.Sleep()
	assert.Equal(t, ans_p, p_test, "Two objects must be the same")
}
