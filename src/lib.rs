mod anger;
mod modules;
pub(crate) mod util;

use anger::table::{Table, column::Column, row::Row};
use chrono::Local;
use modules::{income::Income, user::User, accounting::{Frequency}};
use sycamore::prelude::*;
use util::money::Money;


pub fn start() {
  let columns = vec![
      Column { id: "name", label: "Nome"  }, 
      Column { id: "value", label: "Valor"  },
      Column { id: "origin", label: "Origem"  },
  ];
  
  let rows = vec![
      Row::new(Income { 
        name: "Tatielle Viagem".to_string(),
        amount: Money(20000),
        origin: User,
        date: Local::now(),
        frequency: Frequency::ONCE
      }), 
  ];
  
  
  sycamore::render(|cx| view! { cx,
      Table(columns = columns, rows = rows)
  });
}

//	R$ 200,00	Tatielle Fernandes
//Tatielle Hotel	R$ 193,00	Tatielle Fernandes
//Tatielle Niver	R$ 385,52	Maria Fernandes
//Salario	R$ 9.157,34	Picpay
//Mãe	R$ 509,00	Maria Fernandes
//Tatielle	R$ 50,00	Tatielle Fernandes
//Vale Home Office	R$ 144,99	Picpay
//Vale Home Office 2	R$ 144,99	Picpay
//Viagem Gramado	R$ 261,39	Arthur Fernandes
//Casamento Wendy	R$ 61,00	Wendy Artiaga