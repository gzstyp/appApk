package com.fwtai.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * List或Map的工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月24日 16:25:16
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ToolListOrMap{

	/**判断List是否为空，不为空返回true，否则返回false*/
	public final static boolean verifyEmpty(final List<String> list){
		if(list != null && list.size() > 0)return true;
		return false;
	}
	
	/**判断List是否为空，不为空返回true，否则返回false*/
	public final static boolean verifyEmpty(final ArrayList<String> list){
		if(list != null && list.size() > 0)return true;
		return false;
	}
	
	/**
	 * 获取list里的第0个到第前几Index个的数据(用于已排序的listMap))
	 * @param arrayListHashMap
	 * @param index
	 * @return
	 * @作者 田应平
	 * @返回值类型 ArrayList< HashMap< String,String>>
	 * @创建时间 2015年3月5日 13:27:07 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static ArrayList<HashMap<String, String>> fromZeroToIndex(final ArrayList<HashMap<String, String>> arrayListHashMap, int index){
		ArrayList<HashMap<String, String>> temp = new ArrayList<HashMap<String,String>>();
		if(listOrMapIsNotEmpty(arrayListHashMap)){
			index = index <= 0 ? 1 :index ;
			index = index > arrayListHashMap.size()?arrayListHashMap.size():index;
			for (int i = 0; i < index; i++){
				temp.add(arrayListHashMap.get(i));
			}		
		}
		return temp ;
	}
	
	/**
	 * 对已排序的ListMap获取第index个的数据[当然不排序也适用的]<br/>
	   {欲获取第1个数据，则index=0(是从0下标开始的,0就是第一个);欲获取第2个数据，则index=1；欲获取第3个数据，则index=2}<br />
	   {欲获取倒数第1个数据也就是最后1个,则index=ListMap.size()-1} <br />
	   {欲获取倒数第2个数据也就是最后2个,则index=ListMap.size()-2} <br />
	   {欲获取倒数第3个数据也就是最后3个,则index=ListMap.size()-3} <br />以此类推【注意：temp.size()==temp.size()-1都是获取最后一个】
	 * @param arrayListHashMap
	 * @param index
	 * @return
	 * @作者 田应平
	 * @返回值类型 ArrayList<HashMap<String,String>>
	 * @创建时间 2015年3月5日 18:12:45
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static ArrayList<HashMap<String, String>> getIndex(final ArrayList<HashMap<String, String>> arrayListHashMap, int index){
		final ArrayList<HashMap<String, String>> temp = new ArrayList<HashMap<String,String>>();
		if(listOrMapIsNotEmpty(arrayListHashMap)){
			index = index <= 0 ? 0 :index ;
			index = index >= arrayListHashMap.size()?arrayListHashMap.size()-1:index;
			temp.add(arrayListHashMap.get(index));
		}
		return temp ;
	}
	
	
	/**判断Map是否为空,不为空则返回true，否则返回false*/
	public final static boolean verifyEmpty(final Map<Object, Object> mapObj){
		if(mapObj != null && mapObj.size() > 0)return true ;
		return false ;
	}
	
	/**判断Map是否为空,不为空则返回true，否则返回false*/
	public final static boolean listMapVerify(final List<Map<String, Object>> listMapObj){
		if(listMapObj != null && listMapObj.size() > 0)return true ;
		return false ;
	}
	
	/**判断Map是否为空,不为空则返回true，否则返回false*/
	public final static boolean hashMapVerify(final HashMap<Object, Object> mapObj){
		if(mapObj != null && mapObj.size() > 0)return true ;
		return false ;
	}
	
	/**判断Map是否为空,不为空则返回true，否则返回false*/
	public final static boolean checkEmpty(final HashMap<String, Object> mapObj){
		if(mapObj != null && mapObj.size() > 0)return true ;
		return false ;
	}
	
	/**判断Map是否为空,不为空则返回true，否则返回false*/
	public final static boolean verifyEmpty(final HashMap<String,String> hashMap){
		if(hashMap != null && hashMap.size() > 0)return true ;
		return false ;
	}
	
	/**判断Map是否为空,不为空则返回true，否则返回false*/
	public final static boolean verifyMapEmpty(final Map<String, Object> mapObj){
		if(mapObj != null && mapObj.size() > 0)return true ;
		return false ;
	}
	
	/**判断ListMap是否有值,不为空则返回true，否则返回false*/
	public final static boolean isNotEmpty(final List<Map<Object, Object>> listMap){
		if(listMap != null && listMap.size() > 0)return true ;
		return false ;
	}
	
	/**判断ListMap是否为空,不为空则返回true，否则返回false*/
	public final static boolean listOrMapIsNotEmpty(final ArrayList<HashMap<String, String>> listMap){
		if(listMap != null && listMap.size() > 0)return true ;
		return false ;
	}
	
	/**判断ListMap是否为空,不为空则返回true，否则返回false*/
	public final static boolean listOrMapObjNotEmpty(final ArrayList<HashMap<String, Object>> listMap){
		if(listMap != null && listMap.size() > 0)return true ;
		return false ;
	}
	
	/**判断ListMap是否为空,不为空则返回true，否则返回false*/
	public final static boolean isNotEmpty(final ArrayList<HashMap<String, String>> listMap){
		if(listMap != null && listMap.size() > 0)return true ;
		return false ;
	}
	
	/**判断ListMap是否为空,不为空则返回true，否则返回false*/
	public final static boolean listOrMapObjIsEmpty(final List<Map<String, Object>> listMap){
		if(listMap != null && listMap.size() > 0)return true ;
		return false ;
	}
	
	/**
	 * 对数据类型为List_Map_String_Object冒泡排序-升序
	 * @param list
	 * @return
	 * @作者 田应平
	 * @返回值类型 Integer
	 * @创建时间 2015-3-5 上午10:03:59 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 */
	public final static List<Map<Object, Object>> sortBubbling(final List<Map<Object, Object>> list,String key){
		if(!ToolString.isBlank(key) && isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++){
				Map<Object, Object> tmp0 = list.get(i);
				int number0 = (Integer) tmp0.get(key);
				Map<Object, Object> tmp = null;
				for (int j = i; j < list.size(); j++){
					Map<Object, Object> tmp1 = list.get(j);
					int number1 = (Integer) tmp1.get(key);
					if(number0 > number1){
						tmp = tmp0;
						list.set(i, list.get(j));
						list.set(j, tmp);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 对数据类型为ArrayList_HashMap_String_String排序-升序
	 * @param list
	 * @param key
	 * @return
	 * @作者 田应平
	 * @返回值类型 ArrayList< HashMap< String,String>>
	 * @创建时间 2015-3-5 上午10:11:36 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 */
	public final static ArrayList<HashMap<String, String>> sortBubbling(final ArrayList<HashMap<String, String>> list,String key){
		if(!ToolString.isBlank(key) && listOrMapIsNotEmpty(list)){
			for (int i = 0; i < list.size(); i++){
				HashMap<String, String> tmp0 = list.get(i);
				int number0 = Integer.parseInt(tmp0.get(key));
				HashMap<String, String> tmp = null;
				for (int j = i; j < list.size(); j++){
					HashMap<String, String> tmp1 = list.get(j);
					int number1 = Integer.parseInt(tmp1.get(key));
					if(number0 > number1){
						tmp = tmp0;
						list.set(i, list.get(j));
						list.set(j, tmp);
					}
				}
			}
		}
		return list;
	}
	
	/**对 HashMap排序*/
	public final static Object[] sortHashMap(final HashMap<String,String> hashmap){
		Object[] key_arr = hashmap.keySet().toArray();     
		Arrays.sort(key_arr);
		return key_arr ;
	}
	
	/**去除重复的元素-保持原来的顺序*/
	public final static List<String> listRemoveRepetition(final List<String> list){
		if(verifyEmpty(list)){
			final Set<String> set = new HashSet<String>();
			final ArrayList<String> newList = new ArrayList<String>();
			for (Iterator<String> iter = list.iterator(); iter.hasNext();){
				String element = iter.next();
				if(set.add(element))
					newList.add(element);
			}
			list.clear();
			list.addAll(newList);
			return list;
		}
		return null;
	}
	
	/**去除重复的元素-保持原来的顺序*/
	public final static ArrayList<String> listRemoveRepetition(final ArrayList<String> list){
		if(verifyEmpty(list)){
			final Set<String> set = new HashSet<String>();
			final ArrayList<String> newList = new ArrayList<String>();
			for (Iterator<String> iter = list.iterator(); iter.hasNext();){
				String element = iter.next();
				if(set.add(element))
					newList.add(element);
			}
			list.clear();
			list.addAll(newList);
			return list;
		}
		return null;
	}
	
	/**去除重复的元素-不保持原来的顺序*/
	public final static List<String> removeRepetition(final List<String> list){
		if(verifyEmpty(list)){
			final HashSet<String> hashSet = new HashSet<String>(list);
			list.clear();
			list.addAll(hashSet);
			return list;
		}
		return null;
	}
}