package gsgen.engine;

import gsgen.annotations.Getter;
import gsgen.annotations.Property;
import gsgen.annotations.Setter;
import gsgen.enums.Format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Getter and Setter Generator's Engine.
 * </p>
 * 
 * <p>
 * This engine is responsible for making checks on existing source code and
 * generate new source code.
 * </p>
 * 
 * @author Thiago Alexandre Martins Monteiro (pydawan)
 * 
 */
public abstract class GSGenEngine {	
	public static String applicationDirectory;
	public static String applicationSourceCodeDirectory;
	private static List<String> generatedGetters = null;
	private static List<String> generatedSetters = null;
	public static Map<String, Map<String, List<String>>> generatedSourceCodes = new HashMap<String, Map<String, List<String>>>();
	public static Map<String, List<String>> alreadyCreatedGettersAndSetters = new HashMap<String, List<String>>();

	/**
	 * <p>
	 * Method that generates the getters and setters of a Java project.
	 * </p>
	 */
	public static void generateGettersAndSetters() {
		generateGettersAndSetters(applicationDirectory);
	}

	/**
	 * <p>
	 * Method that generates the getters and setters of a Java project.
	 * </p>
	 * 
	 * @param path
	 *            The directory path of the Java project in the file system.
	 */
	public static void generateGettersAndSetters(String path) {
		if (path != null && !path.trim().isEmpty()) {
			applicationDirectory = path.trim();
			applicationSourceCodeDirectory = String.format("%s%ssrc", applicationDirectory, File.separator);
			// Search by all methods prefixed with get or set the class project.
			searchForGettersAndSetters(applicationSourceCodeDirectory);
			// Generates the code of the get and set missing methods.
			generateGettersAndSettersCode(applicationSourceCodeDirectory);
			// Adds the generated code for each class.
			writeGeneratedSourceCode();
		}
	}

	/**
	 * <p>
	 * Method that search for classes that contain methods with the prefix get
	 * or set.
	 * </p>
	 * 
	 * @param path
	 *            The path of the class file in the file system.
	 */
	private static void searchForGettersAndSetters(String path) {
		File sourceCodeDirectory = new File(path);
		if (sourceCodeDirectory != null && sourceCodeDirectory.exists()) {
			if (sourceCodeDirectory.isDirectory()) {
				File[] sourceCodeDirectoryContents = sourceCodeDirectory.listFiles();
				for (File sourceCodeDirectoryContent : sourceCodeDirectoryContents) {
					if (sourceCodeDirectoryContent.isDirectory()) {
						searchForGettersAndSetters(sourceCodeDirectoryContent.getAbsolutePath());
					} else {
						String filePath = sourceCodeDirectoryContent.getAbsolutePath();
						String classPath = filePath.replace(applicationSourceCodeDirectory, "");
						// Ignore files not ending with the .java extension.
						if (!classPath.endsWith(".java")) {
							continue;
						}
						// Ignores the first dot in the full qualified name of the class.
						classPath = classPath.replace(File.separator, ".").substring(1);
						String fullQualifiedNameOfClass = classPath.replace(".java", "");
						try {
							Class<?> clazz = Class.forName(fullQualifiedNameOfClass);
							if (!clazz.isEnum() && !clazz.isAnnotation() && !clazz.isInterface()) {
								List<String> alreadyCreatedGetterOrSetters = null;
								for (Method method : clazz.getDeclaredMethods()) {
									if (method.getName().startsWith("get") || 
										method.getName().startsWith("set")) {
										if (alreadyCreatedGetterOrSetters == null) {
											alreadyCreatedGetterOrSetters = new ArrayList<String>();
											alreadyCreatedGetterOrSetters.add(method.getName());
										} else {
											alreadyCreatedGetterOrSetters.add(method.getName());
										}
									} else {

									}
								}
								if (alreadyCreatedGetterOrSetters != null) {
									alreadyCreatedGettersAndSetters.put(filePath, alreadyCreatedGetterOrSetters);
								}
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * Generator of get methods.
	 * </p>
	 * 
	 * @param field
	 *            Attribute for which the get method will be generated
	 * @return The piece of source code for the get method.
	 */
	private static String generateGetter(Field field, Format format) {
		String getter = "";
		if (field != null) {
			StringBuilder getterSourceCode = new StringBuilder();
			if (format.equals(Format.LONG)) {
				getterSourceCode.append(
					String.format(
						"\tpublic %s get%s%s() {\n", 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
							.substring(0, 1)
							.toUpperCase(), 
						field
							.getName()
							.substring(1, field.getName().length())
					)
				);
				getterSourceCode.append(String.format("\t\treturn %s;\n", field.getName()));
				getterSourceCode.append("\t}\n");
			} else if (format.equals(Format.SHORT)) {
				getterSourceCode.append(
					String.format(
						"\tpublic %s %s() {\n", 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
					)
				);
				getterSourceCode.append(String.format("\t\treturn %s;\n", field.getName()));
				getterSourceCode.append("\t}\n");
			} else if (format.equals(Format.BOTH)) {
				getterSourceCode.append(
					String.format(
						"\tpublic %s get%s%s() {\n", 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
							.substring(0, 1)
							.toUpperCase(), 
						field
							.getName()
							.substring(1, field.getName().length())
					)
				);
				getterSourceCode.append(String.format("\t\treturn %s;\n", field.getName()));
				getterSourceCode.append("\t}\n\n");
				getterSourceCode.append(
					String.format(
						"\tpublic %s %s() {\n", 
						field
							.getType()
							.getSimpleName(), 
						field.getName()
					)
				);
				getterSourceCode.append(
					String.format(
						"\t\treturn get%s%s();\n", 
						field
							.getName()
							.substring(0, 1)
							.toUpperCase(),
						field
							.getName()
							.substring(1, field.getName().length())
					)
				);
				getterSourceCode.append("\t}\n");
			} else {

			}
			getter = getterSourceCode.toString();
		}
		return getter;
	}

	/**
	 * <p>
	 * Generator of set methods.
	 * </p>
	 * 
	 * @param field
	 *            Attribute for which the set method will be generated
	 * @return The piece of source code for the set method.
	 */
	private static String generateSetter(Field field, Format format) {
		String setter = "";
		if (field != null) {
			StringBuilder setterSourceCode = new StringBuilder();
			if (format.equals(Format.LONG)) {
				setterSourceCode.append(
					String.format(
						"\tpublic void set%s%s(%s %s) {\n", 
						field
							.getName()
							.substring(0, 1)
							.toUpperCase(), 
						field
							.getName()
							.substring(1, field.getName().length()), 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
					)
				);
				setterSourceCode.append(String.format("\t\tthis.%s = %s;\n", field.getName(), field.getName()));
				setterSourceCode.append("\t}\n");
			} else if (format.equals(Format.SHORT)) {
				setterSourceCode.append(
					String.format("\tpublic void %s(%s %s) {\n", 
						field
							.getName(), 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
					)
				);
				setterSourceCode.append(String.format("\t\tthis.%s = %s;\n", field.getName(), field.getName()));
				setterSourceCode.append("\t}\n");
			} else if (format.equals(Format.BOTH)) {
				setterSourceCode.append(
					String.format(
						"\tpublic void set%s%s(%s %s) {\n", 
						field
							.getName()
							.substring(0, 1)
							.toUpperCase(), 
						field
							.getName()
							.substring(1, field.getName().length()), 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
					)
				);
				setterSourceCode.append(
					String.format(
						"\t\tthis.%s = %s;\n", 
						field.getName(), 
						field.getName()
					)
				);
				setterSourceCode.append("\t}\n\n");
				setterSourceCode.append(
					String.format(
						"\tpublic void %s(%s %s) {\n", 
						field.getName(), 
						field
							.getType()
							.getSimpleName(), 
						field
							.getName()
					)
				);
				setterSourceCode.append(
					String.format(
						"\t\tset%s%s(%s);\n", 
						field
							.getName()
							.substring(0, 1)
							.toUpperCase(), 
						field
							.getName()
							.substring(1, field.getName().length()),
						field
							.getName()
					)
				);
				setterSourceCode.append("\t}\n");
			} else {

			}
			setter = setterSourceCode.toString();
		}
		return setter;
	}

	/**
	 * <p>
	 * Method that stores in the RAM memory the generated source code for each
	 * class according with the method type (get or set).
	 * </p>
	 * 
	 * @param filePath
	 *            Path in the file system for the source file of the class.
	 * @param typeOfGeneratedMethods
	 *            Type of the generated methods or methods section (get or set).
	 * @param generatedMethods
	 *            The source code of the generated methods.
	 */
	private static void generateSourceCode(String filePath, String typeOfGeneratedMethods, List<String> generatedMethods) {
		if (filePath == null || filePath.isEmpty())
			return;
		if (typeOfGeneratedMethods == null || typeOfGeneratedMethods.isEmpty())
			return;
		if (generatedMethods == null || generatedMethods.isEmpty())
			return;
		Map<String, List<String>> sourceCode = null;
		if (generatedSourceCodes.get(filePath) == null) {
			sourceCode = new HashMap<String, List<String>>();
			sourceCode.put(typeOfGeneratedMethods, generatedMethods);
		} else {
			sourceCode = generatedSourceCodes.get(filePath);
			sourceCode.put(typeOfGeneratedMethods, generatedMethods);
		}
		generatedSourceCodes.put(filePath, sourceCode);
	}

	/**
	 * <p>
	 * Method that writes all source code generated in their respective source
	 * code files.
	 * </p>
	 * 
	 */
	private static void writeGeneratedSourceCode() {
		if (generatedSourceCodes != null && !generatedSourceCodes.isEmpty()) {
			RandomAccessFile classFile = null;
			for (Map.Entry<String, Map<String, List<String>>> generatedCode : generatedSourceCodes.entrySet()) {
				String classFilePath = generatedCode.getKey();
				Map<String, List<String>> generatedClassMethods = generatedCode.getValue();
				try {
					classFile = new RandomAccessFile(new File(classFilePath), "rw");
					boolean writeCode = true;
					// Sets the file pointer before the end of the class to
					// append the generated code.
					classFile.seek(classFile.length() - "\n}".length());
					for (String getter : generatedClassMethods.get("getters")) {
						if (alreadyCreatedGettersAndSetters.get(classFilePath) != null) {
							for (String alreadyCreatedGetterOrSetter : alreadyCreatedGettersAndSetters.get(classFilePath)) {
								if (getter.contains(alreadyCreatedGetterOrSetter)) {
									writeCode = false;
									break; // quits the inner loop.
								} else {
									writeCode = true;
								}
							}
						}
						if (writeCode) {
							classFile.writeBytes("\n");
							classFile.writeBytes(getter);
						}
					}
					for (String setter : generatedClassMethods.get("setters")) {
						if (alreadyCreatedGettersAndSetters.get(classFilePath) != null) {
							for (String alreadyCreatedGetterOrSetter : alreadyCreatedGettersAndSetters.get(classFilePath)) {
								if (setter.contains(alreadyCreatedGetterOrSetter)) {
									writeCode = false;
									break;
								} else {
									writeCode = true;
								}
							}
						}
						if (writeCode) {
							classFile.writeBytes("\n");
							classFile.writeBytes(setter);
						}
					}
					if (writeCode) {
						classFile.writeBytes("\n}");
					}
					classFile.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * <p>
	 * Method that generate the source code of getters and setters for all .java
	 * files in a project.
	 * </p>
	 * 
	 * @param path 
	 * The path of the directory project in the filesystem.
	 */
	private static void generateGettersAndSettersCode(String path) {
		File sourceDirectory = new File(path);
		if (sourceDirectory != null && sourceDirectory.exists()) {
			if (sourceDirectory.isDirectory()) {
				File[] sourceDirectoryContents = sourceDirectory.listFiles();
				for (File sourceDirectoryContent : sourceDirectoryContents) {
					if (sourceDirectoryContent.isDirectory()) {
						generateGettersAndSettersCode(sourceDirectoryContent.getAbsolutePath());
					} else {
						String filePath = sourceDirectoryContent.getAbsolutePath();
						String classPath = filePath.replace(applicationSourceCodeDirectory, "");
						// Ignore files not ending with the .java extension.
						if (!classPath.endsWith(".java")) {
							continue;
						}
						// Ignores the first dot in the full qualified name of the class.
						classPath = classPath.replace(File.separator, ".").substring(1);
						String fullQualifiedNameOfClass = classPath.replace(".java", "");
						try {
							Class<?> clazz = Class.forName(fullQualifiedNameOfClass);
							if (!clazz.isEnum() && !clazz.isAnnotation() && !clazz.isInterface()) {
								generatedGetters = new ArrayList<String>();
								generatedSetters = new ArrayList<String>();
								for (Field field : clazz.getDeclaredFields()) {
									if (field.isAnnotationPresent(Getter.class)) {
										generatedGetters.add(generateGetter(field,field.getAnnotation(Getter.class).format()));
									} else if (field.isAnnotationPresent(Setter.class)) {
										generatedSetters.add(generateSetter(field,field.getAnnotation(Setter.class).format()));
									} else if (field.isAnnotationPresent(Property.class)) {
										generatedGetters.add(generateGetter(field, field.getAnnotation(Property.class).format()));
										generatedSetters.add(generateSetter(field, field.getAnnotation(Property.class).format()));
									} else {

									}
								}
								if (generatedGetters != null) {
									if (!generatedGetters.isEmpty()) {
										generateSourceCode(filePath, "getters", generatedGetters);
									} else {
										generatedGetters = null;
									}
								}
								if (generatedSetters != null) {
									if (!generatedSetters.isEmpty()) {
										generateSourceCode(filePath, "setters", generatedSetters);
									} else {
										generatedSetters = null;
									}
								}
								System.gc(); // calling the Garbage Collector.
							}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}