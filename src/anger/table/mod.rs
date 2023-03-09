pub mod column;

use sycamore::prelude::*;

use self::column::Column;


#[derive(Prop)]
pub struct Properties<'a> {
    columns: Vec<Column<'a>>,
}


#[component]
pub fn Table<G: Html>(cx: Scope, props: Properties) -> View<G> {
    let columns = columns(cx, props.columns);

    view! { cx,
        table {
            thead {
                tr {
                    th { (columns) }
                }
            }

            tbody {
                tr {
                    td { "b tody" }
                }
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