package store_test

import (
	"testing"

	"github.com/LilithCG/3254_Saityrly_TBD/2022_2/people_service/service/store"
)

func TestNewStore(t *testing.T) {
	conn, err := store.GetConnString()
	if err != nil {
		t.Errorf("invalid connection string: %s", err)
	}
	if _, err := store.NewStore(conn); err != nil {
		t.Error(err)
	}
}

func TestListPeople(t *testing.T) {
	conn, err := store.GetConnString()
	if err != nil {
		t.Errorf("invalid connection string: %s", err)
	}
	st, _ := store.NewStore(conn)
	_, err = st.ListPeople()
	if err != nil {
		t.Error(err)
	}
}

func TestGetPeopleById(t *testing.T) {
	conn, err := store.GetConnString()
	if err != nil {
		t.Errorf("invalid connection string: %s", err)
	}
	st, _ := store.NewStore(conn)
	_, err = st.GetPeopleByID("1")
	if err != nil {
		t.Error(err)
	}
}
