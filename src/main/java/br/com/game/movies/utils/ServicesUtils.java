package br.com.game.movies.utils;

import java.util.List;

public class ServicesUtils {
	
	public static <T> T getValueFromListByIndex(List<T> list, Integer index)  {
		try {
			return list.get(index);
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
}
