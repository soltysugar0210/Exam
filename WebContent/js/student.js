function updateClassNames() {
    const schoolId = document.getElementById("school_id").value;
    const classSelect = document.getElementById("class_name");
    classSelect.innerHTML = '<option value="">選択してください</option>';

    if (schoolId && !isNaN(schoolId)) {
        fetch('/kokushimusou/GetClasses.action?school_id=' + encodeURIComponent(schoolId))
            .then(response => {
                if (!response.ok) throw new Error('HTTP ' + response.status);
                return response.json();
            })
            .then(data => {
                if (data.error) {
                    alert(data.error);
                    return;
                }
                data.classNames.forEach(className => {
                    const option = document.createElement("option");
                    option.value = className;
                    option.text = className;
                    classSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                alert('クラスの取得に失敗しました。');
            });
    } else {
        alert('有効な学校コードを入力してください。');
    }
}

function validateForm() {
    const id = document.getElementsByName("id")[0].value;
    const schoolId = document.getElementsByName("school_id")[0].value;
    const className = document.getElementsByName("class_name")[0].value;
    const name = document.getElementsByName("name")[0].value;
    const entranceYear = document.getElementsByName("entrance_year")[0].value;

    if (!id || !schoolId || !className || !name || !entranceYear) {
        alert("すべての項目を入力してください。");
        return false;
    }
    if (isNaN(id) || isNaN(schoolId) || isNaN(entranceYear)) {
        alert("学生番号、学校コード、入学年は数値を入力してください。");
        return false;
    }
    return true;
}