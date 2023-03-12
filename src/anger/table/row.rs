pub trait RowValue: Clone + PartialEq {
    fn key(&self)-> String;
}

#[derive(Debug, Clone, PartialEq, Hash)]
pub struct Row<T: RowValue> {
    value: Value<T>
}


impl<T: RowValue> Row<T> {
    pub fn new(value: T) -> Self {
        Row { value: Value { value } }
    }

    pub fn key(&self) -> String {
        self.value.key()
    }
}

#[derive(Debug, Clone, PartialEq, Hash)]
pub struct Value<T> {
    value: T
}

impl<T: RowValue> Value<T> {
    pub fn key(&self) -> String {
        self.value.key()
    }
}