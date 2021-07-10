package java8;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rexex {

	public static void main(String[] args) throws Exception {
		String code ="Do me a favor? Fetch my <SCRIPT> this is javascript </script> favorite.";
		
		List<Pattern> pattrenList =  new ArrayList<>(); 
		
		pattrenList.add(Pattern.compile("<\\?php (.*) ?>",Pattern.CASE_INSENSITIVE));
		pattrenList.add(Pattern.compile("<script>(.*)</script>",Pattern.CASE_INSENSITIVE));
				
		for(Pattern pattren :  pattrenList)
		{
			boolean isMatchFound = pattren.matcher(code).find();
			System.out.println(pattren+"  "+isMatchFound);
			if(isMatchFound)
			{
				throw new Exception("Invalid Stirng");
			}
		}
		
		
		//code to remove php scripts
		//System.out.println(code.replaceAll("<\\?php (.*) \\?>", ""));
	}

}
