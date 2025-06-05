function validateForm() {
    const id = document.getElementsByName("id")[0].value;
    const schoolId = document.getElementsByName("school_id")[0].value;
    const name = document.getElementsByName("name")[0].value;
    const password = document.getElementsByName("password")[0].value;
    const privilege = document.getElementsByName("privilege")[0].value;

    if (!id || !schoolId || !name || !password || !privilege) {
        alert("すべての項目を入力してください。");
        return false;
    }
    if (isNaN(id) || isNaN(schoolId) || isNaN(privilege)) {
        alert("教師ID、学校コード、権限は数値を入力してください。");
        return false;
    }
    return true;
}