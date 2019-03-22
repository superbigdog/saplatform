package com.huawei.bean;

import java.util.List;
import java.util.Map;

/**
 * 此表不对应
 * @author lWX573630
 *
 */
public class Widget {
	
	private int id;
	private String widgetName;
	private String widgetXpath;
	private int parentWidgetId;
	private String description;
	private Integer softversionId;
	private Map<String , Widget> map;
	
	private String index = "";
	
	private String NAF; /*可操作控件智商的浮动控件，为true时也可点击（存疑）*/
	private String text;
	private String resource_id;
	private String klass; /*控件的class属性，因为和Class和class冲突，所以使用klass*/
	private String packageName; /*控件的package属性，因package关键字不能使用*/
	private String content_desc;
	private String bindFunction = "";
	
	/**Echarts 扩展*/
	private String name;
	private List<Widget> children;
	
	public String getName() {
		return name;
	}

	public List<Widget> getChildren() {
		return children;
	}

	public Map<String, Widget> getChildMap() {
		return map;
	}
	
	public void setChildMap(Map<String,Widget> map) {
		this.map=map;
	}

	public String getBindFunction() {
		return bindFunction;
	}
	
	public void setBindFunction(String bindFunction) {
		if ("".equals(this.bindFunction)) {
			this.bindFunction = bindFunction;
		}else {
			this.bindFunction = this.bindFunction + "," + bindFunction;
		}
	}
	private List<Integer> bounds; /*控件的位置属性*/
	
	public String getNAF() {
		return NAF;
	}
	public void setNAF(String nAF) {
		NAF = nAF;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public String getKlass() {
		return klass;
	}
	public void setKlass(String klass) {
		this.klass = klass;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getContent_desc() {
		return content_desc;
	}
	public void setContent_desc(String content_desc) {
		this.content_desc = content_desc;
	}
	public List<Integer> getBounds() {
		return bounds;
	}
	public void setBounds(List<Integer> bounds) {
		this.bounds = bounds;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
		this.name=widgetName;
	}

	public String getWidgetXpath() {
		return widgetXpath;
	}

	public void setWidgetXpath(String widgetXpath) {
		this.widgetXpath = widgetXpath;
	}

	public int getParentWidgetId() {
		return parentWidgetId;
	}

	public void setParentWidgetId(int parentWidgetId) {
		this.parentWidgetId = parentWidgetId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Integer getSoftversionId() {
		return softversionId;
	}

	public void setSoftversionId(Integer softversionId) {
		this.softversionId = softversionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAF == null) ? 0 : NAF.hashCode());
		result = prime * result + ((bindFunction == null) ? 0 : bindFunction.hashCode());
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((content_desc == null) ? 0 : content_desc.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((klass == null) ? 0 : klass.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
		result = prime * result + parentWidgetId;
		result = prime * result + ((resource_id == null) ? 0 : resource_id.hashCode());
		result = prime * result + ((softversionId == null) ? 0 : softversionId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((widgetName == null) ? 0 : widgetName.hashCode());
		result = prime * result + ((widgetXpath == null) ? 0 : widgetXpath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Widget other = (Widget) obj;
		if (NAF == null) {
			if (other.NAF != null)
				return false;
		} else if (!NAF.equals(other.NAF))
			return false;
		if (bindFunction == null) {
			if (other.bindFunction != null)
				return false;
		} else if (!bindFunction.equals(other.bindFunction))
			return false;
		if (bounds == null) {
			if (other.bounds != null)
				return false;
		} else if (!bounds.equals(other.bounds))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (content_desc == null) {
			if (other.content_desc != null)
				return false;
		} else if (!content_desc.equals(other.content_desc))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (klass == null) {
			if (other.klass != null)
				return false;
		} else if (!klass.equals(other.klass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		if (parentWidgetId != other.parentWidgetId)
			return false;
		if (resource_id == null) {
			if (other.resource_id != null)
				return false;
		} else if (!resource_id.equals(other.resource_id))
			return false;
		if (softversionId == null) {
			if (other.softversionId != null)
				return false;
		} else if (!softversionId.equals(other.softversionId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (widgetName == null) {
			if (other.widgetName != null)
				return false;
		} else if (!widgetName.equals(other.widgetName))
			return false;
		if (widgetXpath == null) {
			if (other.widgetXpath != null)
				return false;
		} else if (!widgetXpath.equals(other.widgetXpath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Widget [id=" + id + ", widgetName=" + widgetName + ", widgetXpath=" + widgetXpath + ", parentWidgetId="
				+ parentWidgetId + ", description=" + description + ", softversionId=" + softversionId + ", index="
				+ index + ", NAF=" + NAF + ", text=" + text + ", resource_id=" + resource_id + ", klass=" + klass
				+ ", packageName=" + packageName + ", content_desc=" + content_desc + ", bindFunction=" + bindFunction
				+ ", name=" + name + ", children=" + children + ", bounds=" + bounds + "]";
	}
	
	
	
}
