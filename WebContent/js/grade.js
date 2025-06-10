function fetchSubjects() {
    const schoolId = document.getElementById("school_id").value;
    const subjectSelect = document.getElementById("subject_id");
    subjectSelect.innerHTML = '<option value="">選択してください</option>';

    if (schoolId && !isNaN(schoolId)) {
        fetch('/kokushimusou/GetSubjects.action?school_id=' + encodeURIComponent(schoolId))
            .then(response => {
                if (!response.ok) throw new Error('HTTP ' + response.status);
                return response.json();
            })
            .then(data => {
                if (data.error) {
                    alert(data.error);
                    return;
                }
                data.subjects.forEach(subject => {
                    const option = document.createElement("option");
                    option.value = subject.id;
                    option.text = subject.name;
                    subjectSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error:', error);
                alert('科目の取得に失敗しました。');
            });
    }
}

function fetchStudentName() {
    const studentId = document.getElementById("student_id").value;
    const studentNameSpan = document.getElementById("student_name");

    if (studentId && !isNaN(studentId)) {
        fetch('/kokushimusou/GetStudent.action?student_id=' + encodeURIComponent(studentId))
            .then(response => {
                if (!response.ok) throw new Error('HTTP ' + response.status);
                return response.json();
            })
            .then(data => {
                if (data.error) {
                    studentNameSpan.textContent = '（生徒が見つかりません）';
                    studentNameSpan.style.color = 'red';
                } else {
                    studentNameSpan.textContent = '（' + data.name + '）';
                    studentNameSpan.style.color = 'black';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                studentNameSpan.textContent = '（エラーが発生しました）';
                studentNameSpan.style.color = 'red';
            });
    } else {
        studentNameSpan.textContent = '';
    }
}

function validateForm() {
    const schoolId = document.getElementsByName("school_id")[0].value;
    const studentId = document.getElementsByName("student_id")[0].value;
    const subjectId = document.getElementsByName("subject_id")[0].value;
    const count = document.getElementsByName("count")[0].value;
    const score = document.getElementsByName("score")[0].value;

    if (!schoolId || !studentId || !subjectId || !count || !score) {
        alert("すべての項目を入力してください。");
        return false;
    }
    if (isNaN(schoolId) || isNaN(studentId) || isNaN(subjectId) || isNaN(count) || isNaN(score)) {
        alert("学校コード、学生番号、科目、回数、点数は数値を入力してください。");
        return false;
    }
    if (count < 1) {
        alert("回数は1以上の値を入力してください。");
        return false;
    }
    if (score < 0 || score > 100) {
        alert("点数は0から100の範囲で入力してください。");
        return false;
    }
    return true;
}