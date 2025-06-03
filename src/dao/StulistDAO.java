package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.StulistBean;

// 入力機能(search)
public class StulistDAO extends DAO {
public void insert(StulistBean Sl) throws Exception {

        try (Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(
                "INSERT INTO student (id, school_id, class_name,name,entrace_year,student_flag) VALUES (?, ?, ?)")) {

                st.setInt(1, Sl.getId());
                st.setInt(2, Sl.getSchool_id());
                st.setString(3, Sl.getClass_name());
                st.setString(4, Sl.getName());
                st.setInt(5, Sl.getEntrance_year());
                st.setBoolean(6, Sl.getStudent_flag());
                st.executeUpdate();
        }

	}

// 削除機能(delete)
public int deleteByName(String name) throws Exception {
    int count = 0;

    // 書籍名だけですが、著者名でも実行できるようにしたい

    String sql = "DELETE FROM student WHERE id = ?";

    try (Connection con = getConnection();
         PreparedStatement st = con.prepareStatement(sql)) {

        st.setString(1, name);
        count = st.executeUpdate();
    }

    return count;
}

// 検索機能(select)
public List<StulistBean> search(String keyword) throws Exception {
    List<StulistBean> list = new ArrayList<>();

    try (Connection con = getConnection();
         PreparedStatement st = con.prepareStatement(
             "SELECT * FROM booklist WHERE name LIKE ? OR author LIKE ?")) {

        String likeKeyword = "%" + keyword + "%";
        st.setString(1, likeKeyword);
        st.setString(2, likeKeyword);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            StulistBean Sl = new StulistBean();
            Sl.setId(rs.getInt("id"));
            Sl.setName(rs.getString("name"));
            Sl.setClass_name(rs.getString("class_name"));
            list.add(Sl);
        }
    }
    return list;
}

}
