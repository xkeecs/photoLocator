package com.photolocator.photosave;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;

import com.photolocator.cassandra.CassandraCallback;
import com.photolocator.cassandra.CassandraDataUnit;
import com.photolocator.cassandra.CassandraFunction;

public class NamesParser
{

	Item objItem;
	List<Item> listArray;
	SetbackList ra;

	CassandraFunction cf;

	public NamesParser(SetbackList ra)
	{
		this.ra = ra;
		cf = new CassandraFunction(new CassandraCallback()
		{

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.photolocator.cassandra.CassandraCallback#dataReaded(java.util.ArrayList)
			 */
			@Override
			public void dataReaded(ArrayList<CassandraDataUnit> cdus)
			{
				// TODO Auto-generated method stub
				super.dataReaded(cdus);
				try
				{

					listArray = new ArrayList<Item>();

					// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					// DocumentBuilder db = dbf.newDocumentBuilder();
					// Document doc = db.parse(new URL(url).openStream());
					//
					// doc.getDocumentElement().normalize();

					// NodeList nList = doc.getElementsByTagName("item");

					for (int temp = 0; temp < cdus.size(); temp++)
					{

						objItem = new Item();

						objItem.setId(cdus.get(temp).getUserName());
						objItem.setTitle(cdus.get(temp).getLocationName());
						objItem.setPubdate(cdus.get(temp).getTime().toString());
						objItem.setBitmap(cdus.get(temp).getBitmap());

						listArray.add(objItem);
					}

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				NamesParser.this.ra.setbackList(listArray);
			}

		});

	}

	public void getData(Context context, String username)
	{

		cf.readDataByUserName(context, username);
	}

	private static String getTagValue(String sTag, Element eElement)
	{
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();

	}
}
