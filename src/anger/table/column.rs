
pub struct Column<'a> {
    pub id: &'a str,
    pub label: &'a str
}


impl<'a> Column<'a> {
    pub fn label(&self) -> String {
        String::from(self.label)
    }
}