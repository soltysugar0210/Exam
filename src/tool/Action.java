package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Actionクラスの基幹クラスを作成しておきます
public abstract class Action {

    // すべてのActionクラスがもつメソッド「execute」も作っておきます
	public abstract String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception;
}
