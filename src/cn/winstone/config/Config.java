package cn.winstone.config;

import cn.winstone.utils.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Winstone
 * @date 2020/9/17 - 1:27 下午
 */
public class Config {
	public class ConfigItem {
		private String name;
		private String src;
		private String out;
		private boolean hasDir;

		public ConfigItem(String name, String src, String out, boolean hasDir) {
			this.name = name;
			this.src = src;
			this.out = out;
			this.hasDir = hasDir;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getOut() {
			return out;
		}

		public void setOut(String out) {
			this.out = out;
		}

		public boolean isHasDir() {
			return hasDir;
		}

		public void setHasDir(boolean hasDir) {
			this.hasDir = hasDir;
		}
	}

	private Map<String, List<String>> types = new HashMap<>();
	private List<String> keys = new ArrayList<>();

	public Config(String path) {
		File file = new File(path);
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String s = "";
			String key = null;
			List<String> value = new ArrayList<>();
			while ((s = br.readLine()) != null) {
				s = replaceAllBlank(s);
				if (s.endsWith("[")) {
					key = s.substring(0, s.length() - 1);
					continue;
				} else if (s.endsWith("]")) {
					types.put(key, value);
					keys.add(key);
					value = new ArrayList<>();
					continue;
				} else {
					if (s.endsWith(",")) {
						s = s.substring(0, s.length() - 1);
					}
					if (!s.equals("")) {
						value.add(s);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String replaceAllBlank(String str) {
		String s = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			/*   \n 回车(\u000a)   \t 水平制表符(\u0009)   \s 空格(\u0008)   \r 换行(\u000d)   */
			Matcher m = p.matcher(str);
			s = m.replaceAll("");
		}
		return s;
	}

	public String getFileKindByPath(String path) {
		String type = FileUtils.getFileTypeByPath(path);
		return getFileKindByType(type);
	}

	public String getFileKindByName(String name) {
		String type = FileUtils.getFileType(name);
		return getFileKindByType(type);
	}

	public String getFileKindByType(String type) {
		for (Map.Entry<String, List<String>> entry : types.entrySet()) {
			if (entry.getKey().contains("PATH::")) {
				continue;
			}
			List<String> typeList = entry.getValue();
			for (String isType : typeList) {
				if (isType.equals(type)) {
					String kind = entry.getKey();
					kind = kind.substring(0, kind.indexOf(":"));
					return kind;
				}
			}
		}
		return "其他";
	}

	public List<ConfigItem> getPath() {
		List<ConfigItem> configItems = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : types.entrySet()) {
			if (entry.getKey().contains("PATH::")) {
				String name = entry.getKey();
				name = name.substring(name.lastIndexOf(":") + 1);
				List<String> settings = entry.getValue();
				String src = null;
				String out = null;
				String hasDir = null;
				for (String setting : settings) {
					if (setting.contains("source:")) {
						src = setting.substring(7);
					}
					if (setting.contains("out:")) {
						out = setting.substring(4);
					}
					if (setting.contains("hasDir:")) {
						hasDir = setting.substring(7);
					}
				}
				if (src != null && out != null && hasDir != null && name != null) {
					configItems.add(new ConfigItem(name, src, out, Boolean.parseBoolean(hasDir)));
				}
			}
		}
		return configItems;
	}
}
