pub mod column;
pub mod row;

use sycamore::prelude::*;

use self::{column::Column, row::{Row, RowValue}};


#[derive(Prop)]
pub struct Properties<'a, T: RowValue> {
    columns: Vec<Column<'a>>,
    rows: Vec<Row<T>>
}


#[component]
pub fn Table<G: Html, T: RowValue>(cx: Scope, props: Properties<T>) -> View<G> {
    let columns = columns(cx, props.columns);
    //let rows = create_signal(cx, create_ref(cx, props.rows));
    let rows = create_signal(cx, props.rows);

    view! { cx,
        table {
            thead {
                tr {
                    th { (columns) }
                }
            }

            tbody {
                Keyed(
                    iterable=rows,
                    view=|cx, x| view! { cx,
                        tr {
                            td { (x.key()) }
                        }
                    },
                    key=|x| x.key(),
                )
                
            }
        }
    }
}


#[component]
fn columns<G: Html>(cx: Scope, columns: Vec<Column>) -> View<G> {
    View::new_fragment(
        columns.iter().map(|x| {
            let label = x.label();
            view! { cx, 
                th { (label) } 
            }
        })
        .collect()
    )
}