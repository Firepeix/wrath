mod anger;

use anger::table::{Table, column::Column};
use sycamore::prelude::*;


pub fn start() {
    sycamore::render(|cx| view! { cx,
        Table(columns = vec![Column { id: "1", label: "Teste1"  }, Column { id: "2", label: "Teste2"  } ])
    });
}