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
pub fn Table<'a, G: Html, T: RowValue + 'a>(cx: Scope<'a>, props: Properties<T>) -> View<G> {
    let header = Header(cx, props.columns);
    let body : View<G> = Body(cx, props.rows);

    view! { cx,
        table {
            thead { (header) }

            tbody { (body) }
        }
    }
}


#[component]
fn Header<G: Html>(cx: Scope, columns: Vec<Column>) -> View<G> {
    let columns = View::new_fragment(
        columns.iter().map(|x| {
            let label = x.label();
            view! { cx, 
                th { (label) } 
            }
        })
        .collect()
    );

    view!(
        cx,
        tr {
            th { (columns) }
        }
    )
}

#[component]
fn Body<'a, G: Html, R: RowValue + 'a>(cx: Scope<'a>, rows: Vec<Row<R>>) -> View<G> {
    let rows = create_signal(cx, rows);

    view!(cx,
        Keyed(
            iterable=rows,
            view=|cx, x| view! { cx,
                tr {
                    td { (x.key()) }
                }
            },
            key= |x| x.key(),
        )
    )
}