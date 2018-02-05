
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Person {
	public String firstName;
	public String Lastname;
	public Integer age;
	public String company;

	public static void main(String[] args) {
		
		List<Person> persons = beanMapperFile(Person.class, "F:\\Student\\eclipse\\workspace\\data\\files\\Persons.txt");
		
		persons.forEach(System.out::println);
		
		beanWriterFile(persons, "F:\\Student\\eclipse\\workspace\\data\\files\\Persons2.txt",true,"->");
		 
		
//		try {
//			Path newDir=Files.createDirectory(path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Class pClass = Person.class;
		
//		Field[] fields = pClass.getFields();
//		Arrays.stream(fields).forEach(s->System.out.println(s.getName()));
//		Person p=(Person)mapper(pClass,"vishal|||Wells");
//		System.out.println(p);

	}
	
	static <T> List<T> beanMapperFile(Class<T> clazz,String filepath){
		
		
		Path path = Paths.get(filepath);
		
		System.out.println("File exists : "+Files.exists(path, LinkOption.NOFOLLOW_LINKS));
		
		List<T> resLst= new ArrayList<T>();
		
		try {
			Stream<Object> lines = Files.lines(path).parallel().map(s->mapper(clazz,s));
			lines.forEach(s-> resLst.add((T) s));
			
			//List perLst=(List) lines.collect(Collectors.toList());
			return resLst;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	static private Object mapper(Class clazz, String values){
		 try {
			Object tmp = clazz.newInstance();
			
			
			Field[] fields = clazz.getFields();
			String [] res = values.split("\\|") ;
			int count=0;
				for(Field s:fields){
					if(count<res.length&&!(res[count].equals("")))
					s.set(clazz.cast(tmp),s.getType().getConstructor(String.class).newInstance( res[count]));
					
				count++;
			
		 } return tmp;
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		
			}
		return null;
		
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", Lastname=" + Lastname
				+ ", age=" + age + ", company=" + company + "]";
	}
	
	static <T> boolean beanWriterFile(List<T> list,String filepath){
		return beanWriterFile(list,filepath,false,null);
		
	}
	static <T> boolean beanWriterFile(List<T> list,String filepath,String delimiter){
		return beanWriterFile(list,filepath,false,delimiter);
		
	}
static <T> boolean beanWriterFile(List<T> list,String filepath,boolean header,final String delimiter){
		
		
		Path path = Paths.get(filepath);
		try {
			Files.createFile(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("File exists : "+Files.exists(path, LinkOption.NOFOLLOW_LINKS));
		
		try(BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8")))
		
		 {	if(header){int count=1;
			Field[] fields = list.get(0).getClass().getFields();
			 
			StringBuilder sb= new StringBuilder("");
				for(Field fld:fields){
					if(count++<fields.length)
					sb.append(fld.getName()+delimiter);
					else
						sb.append(fld.getName());
							
		 } 		
				writer.write(sb.toString());
				writer.newLine();
				writer.flush();
		 }
		list.stream().forEach(s->writer(s, writer,delimiter));
			//List perLst=(List) lines.collect(Collectors.toList());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}

static private <T> void writer(T s, BufferedWriter writer,String delimiter){
	 try {
		 if(delimiter==null||delimiter.equalsIgnoreCase(""))
				delimiter ="|" ;
		
		int count=1;
		Field[] fields = s.getClass().getFields();
//		String delimiter ="|" ;
		StringBuilder sb= new StringBuilder("");
			for(Field fld:fields){
				if(count++<fields.length)
				sb.append((fld.get(s)==null?"":fld.get(s))+delimiter);
				else
					sb.append(fld.get(s)==null?"":fld.get(s));
						
	 } 		
			writer.write(sb.toString());
			writer.newLine();
			writer.flush();
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	
}


}
