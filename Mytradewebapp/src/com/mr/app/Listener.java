package com.mr.app;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.mr.data.Daypivot;
import com.mr.data.Daysma50;
import com.mr.data.Daystochdata;
import com.mr.data.HistoricHighLow;
import com.mr.data.MyindicatorvaluesDay;
import com.mr.data.MyindicatorvaluesWeek;
import com.mr.data.StochIndicData;
import com.mr.data.StochasticFinalval;
import com.mr.data.StockOtherTechnicals;
import com.mr.data.Stocktwodaystat;
import com.mr.data.Stocktwomonthstat;
import com.mr.data.Stocktwoweekstat;
import com.mr.data.TickData;
import com.mr.data.Weekpivot;
import com.mr.data.Weeksma50;
import com.mr.data.WeekstochasticFinalval;
import com.mr.data.Weekstochdata;
import com.mr.hourlydata.Hourdatadownloader;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import datainsert.MainDownload;

/**
 * Servlet implementation class Listener
 */
@WebServlet("/Listener")
public class Listener implements ServletContextListener {
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Listener() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		ServletContext ctx = servletContextEvent.getServletContext();
		Connection conn = null;
		Context envCtx;
		int stockcount = 0;
		try {
			Context initCtx = (Context) new InitialContext();
			envCtx = (Context) ((Context) initCtx).lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/UsersDB");
			conn = ds.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			MainDownload.updateeod();
			ctx.setAttribute("weeksma50", getweeksma50(conn));
			ctx.setAttribute("Weekpivot", getweekpivot(conn));
			ctx.setAttribute("Stocktwoweekstats", gettwoweekstats(conn));
			ctx.setAttribute("nse200", getnse200(conn));

			ctx.setAttribute("Stocktwomonthstats", gettwomonthstats(conn));
			ctx.setAttribute("StockDaytwodaystats", gettwodaystats(conn));
			ctx.setAttribute("Daysma50", getDaysma50(conn));
			ctx.setAttribute("Daypivot", getDaypivot(conn));
			// ctx.setAttribute("Dayeodstochastic", getDaystochastic(conn));
			// ctx.setAttribute("PrevDayeodstochastic",
			// getPrevDaystochastic(conn));
			ctx.setAttribute("HistoricHL", getHistoricHL(conn));
			ctx.setAttribute("Week52HL", getWeek52HL(conn));
			ctx.setAttribute("nse100liveStoch", getnse100(conn));
			ctx.setAttribute("WeekintradayPivot", getweekintradaypivot(conn));
			// ctx.setAttribute("EODWR", getEODWR(conn));
			ctx.setAttribute("Dayneweodstochastic", getcalcDaystochastic(conn));
			ctx.setAttribute("weekeodstochastic", gecalcweekstoch(conn));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

	private HashMap<String, StochasticFinalval> getcalcDaystochastic(Connection conn) throws SQLException {
		// TODO Auto-generated method stub

		HashMap<String, StochasticFinalval> daystochKD = new HashMap<String, StochasticFinalval>();
		HashMap<String, String> stockweekclose = new HashMap<String, String>();
		HashMap<String, String> stockweekopen = new HashMap<String, String>();
		HashMap<String, String> stockweeklow = new HashMap<String, String>();
		HashMap<String, String> stockweekhigh = new HashMap<String, String>();
		HashMap<String, String> stockweekvolume = new HashMap<String, String>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select stocksymbol,listagg(CLOSEPRICE,',') within group (order by tradedate asc) CLOSEPRICEs from stockprice where tradedate > sysdate-300 group by stocksymbol");
		while (rs.next()) {
			stockweekclose.put(rs.getString(1), rs.getString(2));

		}
		rs.close();
		stmt.close();

		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery(
				"select stocksymbol,listagg(OPENPRICE,',') within group (order by tradedate asc) OPENPRICE from stockprice where tradedate > sysdate-300 group by stocksymbol");
		while (rs1.next()) {
			stockweekopen.put(rs1.getString(1), rs1.getString(2));

		}
		rs1.close();
		stmt1.close();

		Statement stmt2 = conn.createStatement();
		ResultSet rs2 = stmt2.executeQuery(
				"select stocksymbol,listagg(LOWPRICE,',') within group (order by tradedate asc) LOWPRICEs from stockprice where tradedate > sysdate-300 group by stocksymbol");
		while (rs2.next()) {
			stockweeklow.put(rs2.getString(1), rs2.getString(2));

		}
		rs2.close();
		stmt2.close();

		stmt2 = conn.createStatement();
		rs2 = stmt2.executeQuery(
				"select stocksymbol,listagg(HIGHPRICE,',') within group (order by tradedate asc) HIGHPRICEs from stockprice where tradedate > sysdate-300 group by stocksymbol");
		while (rs2.next()) {
			stockweekhigh.put(rs2.getString(1), rs2.getString(2));

		}
		rs2.close();
		stmt2.close();

		stmt2 = conn.createStatement();
		rs2 = stmt2.executeQuery(
				"select stocksymbol,listagg(STOCKVOLUME,',') within group (order by tradedate asc) STOCKVOLUMEs from stockprice where tradedate > sysdate-300 group by stocksymbol");
		while (rs2.next()) {
			stockweekvolume.put(rs2.getString(1), rs2.getString(2));

		}
		rs2.close();
		stmt2.close();

		Iterator it = stockweekclose.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String stocksymbol = (String) pair.getKey();
			daystochKD.put(stocksymbol, getstochdayindivudual(stocksymbol, stockweekopen, stockweekhigh, stockweeklow,
					stockweekclose, stockweekvolume));
		}
		return daystochKD;

	}

	private StochasticFinalval getstochdayindivudual(String stocksymbol, HashMap<String, String> stockweekopen,
			HashMap<String, String> stockweekhigh, HashMap<String, String> stockweeklow,
			HashMap<String, String> stockweekclose, HashMap<String, String> stockweekvolume) {
		// TODO Auto-generated method stub
		double indick = 0, indicd = 0, wpr = 0, rsi = 0;
		Float openprice, closeprice = 0f, highprice, lowprice;
		Float fopen = 0f, fclose = 0f, fhigh = 0f, flow = 0f;
		double sma50 = 0;
		Float C_openprice = 0f, C_closeprice = 0f, C_highprice = 0f, C_lowprice = 0f;
		Float obvvolume = 0f, currentvolume, C_currentvolume;
		Long timestamp = null;
		Date tickstart, tickend;

		ArrayList<Float> low = new ArrayList<Float>();
		ArrayList<Float> high = new ArrayList<Float>();
		ArrayList<Float> obvvol = new ArrayList<Float>();
		ArrayList<Float> close = new ArrayList<Float>();

		StringTokenizer opentoken = new StringTokenizer(stockweekopen.get(stocksymbol), ",");
		StringTokenizer hightoken = new StringTokenizer(stockweekhigh.get(stocksymbol), ",");
		StringTokenizer lowtoken = new StringTokenizer(stockweeklow.get(stocksymbol), ",");
		StringTokenizer closetoken = new StringTokenizer(stockweekclose.get(stocksymbol), ",");
		StringTokenizer volumetoken = new StringTokenizer(stockweekvolume.get(stocksymbol), ",");

		ArrayList<TickData> tickhash = new ArrayList<TickData>();

		while (opentoken.hasMoreElements()) {
			TickData tickd = new TickData();
			tickd.setOpenprice(Float.valueOf((String) opentoken.nextElement()));
			tickd.setHighprice(Float.valueOf((String) hightoken.nextElement()));
			tickd.setLowprice(Float.valueOf((String) lowtoken.nextElement()));
			tickd.setCloseprice(Float.valueOf((String) closetoken.nextElement()));
			tickd.setVolume(Float.valueOf((String) volumetoken.nextElement()));
			tickhash.add(tickd);
		}

		ArrayList<TickData> Converttick = tickhash;

		for (int ik = 0; ik < Converttick.size(); ik++) {
			float prevclose = 0f;
			TickData Currtickdata = Converttick.get(ik);
			if (ik > 0) {
				TickData prevtickdata = Converttick.get(ik - 1);
				prevclose = prevtickdata.getCloseprice();
				obvvolume = getobvvol(Currtickdata.getHighprice(), Currtickdata.getLowprice(),
						Currtickdata.getCloseprice(), Currtickdata.getVolume(), obvvolume, prevclose);
			} else {
				prevclose = Currtickdata.getCloseprice();
				obvvolume = Currtickdata.getVolume();
			}

			fopen = Currtickdata.getOpenprice();
			fhigh = Currtickdata.getHighprice();
			flow = Currtickdata.getLowprice();
			fclose = Currtickdata.getCloseprice();

			low.add(Currtickdata.getLowprice());
			high.add(Currtickdata.getHighprice());
			close.add(Currtickdata.getCloseprice());
			obvvol.add(obvvolume);
		}

		float[] lowArray = new float[low.size()];
		int k = 0;

		for (Float f : low) {
			lowArray[k++] = (f != null ? f : Float.NaN); // Or whatever default
															// you want.
		}

		float[] highArray = new float[high.size()];
		k = 0;

		for (Float f : high) {
			highArray[k++] = (f != null ? f : Float.NaN); // Or whatever default
															// you want.
		}

		float[] closeArray = new float[close.size()];
		k = 0;

		for (Float f : close) {
			closeArray[k++] = (f != null ? f : Float.NaN); // Or whatever
															// default you want.
		}

		float[] obvArray = new float[obvvol.size()];
		k = 0;

		for (Float f : obvvol) {
			obvArray[k++] = f; // Or whatever default you want.
		}

		// start computation here.
		Float lowdiff;
		Float low_lowdiff = 1000000000f;
		Float high_lowdiff = 0f;

		Float highdiff;
		Float low_highdiff = 1000000000f;
		Float high_highdiff = 0f;

		Integer Length = low.size();
		Core c = new Core();
		double[] closePrice = new double[5000];
		double[] out = new double[5000];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		ArrayList<Float> lowdiffarr = new ArrayList<Float>();
		ArrayList<Float> highdiffarr = new ArrayList<Float>();

		RetCode retCode = c.wma(0, lowArray.length - 1, lowArray, 50, begin, length, out);
		if (retCode == RetCode.Success) {
			for (int i = begin.value; i < length.value + begin.value; i++) {
				lowdiff = lowArray[i] - Float.valueOf(String.valueOf(out[i - begin.value]));
				// System.out.println(i+":"+lowdiff+";"+lowArray[i]+"::"+out[i-begin.value]);
				lowdiffarr.add(lowdiff);
			}
		}

		retCode = c.wma(0, highArray.length - 1, highArray, 50, begin, length, out);
		if (retCode == RetCode.Success) {
			for (int i = begin.value; i < length.value + begin.value; i++) {
				highdiff = highArray[i] - Float.valueOf(String.valueOf(out[i - begin.value]));

				highdiffarr.add(highdiff);
			}
		}

		float[] highdiffArray = new float[highdiffarr.size()];
		k = 0;

		for (Float f : highdiffarr) {
			highdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever
																// default you
																// want.
		}

		float[] lowdiffArray = new float[lowdiffarr.size()];
		k = 0;

		for (Float f : lowdiffarr) {
			lowdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever
																// default you
																// want.
		}

		// lowdiffArray, highdiffarray, obvArray,lowarray ,closeArrayready

		DecimalFormat df1 = new DecimalFormat("#.##");

		double[] vals = getstochvals(lowdiffArray, highdiffArray, obvArray, lowArray, retCode, c);

		indick = Double.valueOf(df1.format(vals[0]));
		indicd = Double.valueOf(df1.format(vals[1]));
		Double p_indick = Double.valueOf(df1.format(vals[2]));
		Double p_indicd = Double.valueOf(df1.format(vals[3]));

		float[] sptnd = getsupertrend(closeArray, highArray, lowArray, c);
		float[] sptnd7 = getsupertrend7(closeArray, highArray, lowArray, c);

		StochasticFinalval st = new StochasticFinalval();
		st.setPrecentk(indick);
		st.setPercentd(indicd);
		st.setPrev_precentk(p_indick);
		st.setPrev_precentd(p_indicd);
		st.setStocksymbol(stocksymbol);
		st.setTrend3down(sptnd[0]);
		st.setTrend3up(sptnd[1]);
		st.setTrend5down(sptnd7[0]);
		st.setTrend5up(sptnd7[1]);
		if (sptnd[2] == -1)
			st.setTrend3("Down");
		else if (sptnd[2] == 1)
			st.setTrend3("Up");
		else
			st.setTrend3("NA");

		if (sptnd7[2] == -1)
			st.setTrend5("Down");
		else if (sptnd7[2] == 1)
			st.setTrend5("Up");
		else
			st.setTrend5("NA");

		return st;
	}

	private HashMap<String, MyindicatorvaluesWeek> gecalcweekstoch(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, MyindicatorvaluesWeek> daystoch = new HashMap<String, MyindicatorvaluesWeek>();
		HashMap<String, String> stockweekclose = new HashMap<String, String>();
		HashMap<String, String> stockweekopen = new HashMap<String, String>();
		HashMap<String, String> stockweeklow = new HashMap<String, String>();
		HashMap<String, String> stockweekhigh = new HashMap<String, String>();
		HashMap<String, String> stockweekvolume = new HashMap<String, String>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select stocksymbol,listagg(weekclose,',') within group (order by tdyw asc) weekcloses from (select stocksymbol,weekclose,to_number(tradeyr||tradeweek) tdyw from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < 250)) group by stocksymbol ");
		while (rs.next()) {
			stockweekclose.put(rs.getString(1), rs.getString(2));

		}
		rs.close();
		stmt.close();

		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery(
				"select stocksymbol,listagg(WEEKOPEN,',') within group (order by tdyw asc) WEEKOPENs from (select stocksymbol,WEEKOPEN,to_number(tradeyr||tradeweek) tdyw from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < 250)) group by stocksymbol");
		while (rs1.next()) {
			stockweekopen.put(rs1.getString(1), rs1.getString(2));

		}
		rs1.close();
		stmt1.close();

		Statement stmt2 = conn.createStatement();
		ResultSet rs2 = stmt2.executeQuery(
				"select stocksymbol,listagg(WEEKLOWLOW,',') within group (order by tdyw asc) WEEKLOWLOWs from (select stocksymbol,WEEKLOWLOW,to_number(tradeyr||tradeweek) tdyw from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < 250)) group by stocksymbol");
		while (rs2.next()) {
			stockweeklow.put(rs2.getString(1), rs2.getString(2));

		}
		rs2.close();
		stmt2.close();

		stmt2 = conn.createStatement();
		rs2 = stmt2.executeQuery(
				"select stocksymbol,listagg(WEEKHIGHHIGH,',') within group (order by tdyw asc) WEEKHIGHHIGHs from (select stocksymbol,WEEKHIGHHIGH,to_number(tradeyr||tradeweek) tdyw from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < 250)) group by stocksymbol");
		while (rs2.next()) {
			stockweekhigh.put(rs2.getString(1), rs2.getString(2));

		}
		rs2.close();
		stmt2.close();

		stmt2 = conn.createStatement();
		rs2 = stmt2.executeQuery(
				"select stocksymbol,listagg(TRADEVOLUME,',') within group (order by tdyw asc) TRADEVOLUMEs from (select stocksymbol,TRADEVOLUME,to_number(tradeyr||tradeweek) tdyw from WEEK_STATS where to_number(TRADEYR||tradeweek) >= (select min(tdyr) from (select distinct to_number(TRADEYR||tradeweek) tdyr from WEEK_STATS order by to_number(TRADEYR||tradeweek) desc) where rownum < 250)) group by stocksymbol");
		while (rs2.next()) {
			stockweekvolume.put(rs2.getString(1), rs2.getString(2));

		}
		rs2.close();
		stmt2.close();

		Iterator it = stockweekclose.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String stocksymbol = (String) pair.getKey();
			daystoch.put(stocksymbol, getstochweekindivudual(stocksymbol, stockweekopen, stockweekhigh, stockweeklow,
					stockweekclose, stockweekvolume));
		}
		return daystoch;
	}

	private MyindicatorvaluesWeek getstochweekindivudual(String stocksymbol, HashMap<String, String> stockweekopen,
			HashMap<String, String> stockweekhigh, HashMap<String, String> stockweeklow,
			HashMap<String, String> stockweekclose, HashMap<String, String> stockweekvolume) {
		// TODO Auto-generated method stub

		MyindicatorvaluesWeek dayst = new MyindicatorvaluesWeek();
		double indick = 0, indicd = 0, wpr = 0, rsi = 0;
		Float openprice, closeprice = 0f, highprice, lowprice;
		Float fopen = 0f, fclose = 0f, fhigh = 0f, flow = 0f;
		double sma50 = 0;
		Float C_openprice = 0f, C_closeprice = 0f, C_highprice = 0f, C_lowprice = 0f;
		Float obvvolume = 0f, currentvolume, C_currentvolume;
		Long timestamp = null;
		Date tickstart, tickend;

		ArrayList<Float> low = new ArrayList<Float>();
		ArrayList<Float> high = new ArrayList<Float>();
		ArrayList<Float> obvvol = new ArrayList<Float>();
		ArrayList<Float> close = new ArrayList<Float>();

		StringTokenizer opentoken = new StringTokenizer(stockweekopen.get(stocksymbol), ",");
		StringTokenizer hightoken = new StringTokenizer(stockweekhigh.get(stocksymbol), ",");
		StringTokenizer lowtoken = new StringTokenizer(stockweeklow.get(stocksymbol), ",");
		StringTokenizer closetoken = new StringTokenizer(stockweekclose.get(stocksymbol), ",");
		StringTokenizer volumetoken = new StringTokenizer(stockweekvolume.get(stocksymbol), ",");

		ArrayList<TickData> tickhash = new ArrayList<TickData>();

		while (opentoken.hasMoreElements()) {
			TickData tickd = new TickData();
			tickd.setOpenprice(Float.valueOf((String) opentoken.nextElement()));
			tickd.setHighprice(Float.valueOf((String) hightoken.nextElement()));
			tickd.setLowprice(Float.valueOf((String) lowtoken.nextElement()));
			tickd.setCloseprice(Float.valueOf((String) closetoken.nextElement()));
			tickd.setVolume(Float.valueOf((String) volumetoken.nextElement()));
			tickhash.add(tickd);
		}

		ArrayList<TickData> Converttick = tickhash;

		for (int ik = 0; ik < Converttick.size(); ik++) {
			float prevclose = 0f;
			TickData Currtickdata = Converttick.get(ik);
			if (ik > 0) {
				TickData prevtickdata = Converttick.get(ik - 1);
				prevclose = prevtickdata.getCloseprice();
				obvvolume = getobvvol(Currtickdata.getHighprice(), Currtickdata.getLowprice(),
						Currtickdata.getCloseprice(), Currtickdata.getVolume(), obvvolume, prevclose);
			} else {
				prevclose = Currtickdata.getCloseprice();
				obvvolume = Currtickdata.getVolume();
			}

			fopen = Currtickdata.getOpenprice();
			fhigh = Currtickdata.getHighprice();
			flow = Currtickdata.getLowprice();
			fclose = Currtickdata.getCloseprice();

			low.add(Currtickdata.getLowprice());
			high.add(Currtickdata.getHighprice());
			close.add(Currtickdata.getCloseprice());
			obvvol.add(obvvolume);
		}

		float[] lowArray = new float[low.size()];
		int k = 0;

		for (Float f : low) {
			lowArray[k++] = (f != null ? f : Float.NaN); // Or whatever default
															// you want.
		}

		float[] highArray = new float[high.size()];
		k = 0;

		for (Float f : high) {
			highArray[k++] = (f != null ? f : Float.NaN); // Or whatever default
															// you want.
		}

		float[] closeArray = new float[close.size()];
		k = 0;

		for (Float f : close) {
			closeArray[k++] = (f != null ? f : Float.NaN); // Or whatever
															// default you want.
		}

		float[] obvArray = new float[obvvol.size()];
		k = 0;

		for (Float f : obvvol) {
			obvArray[k++] = f; // Or whatever default you want.
		}

		// start computation here.
		Float lowdiff;
		Float low_lowdiff = 1000000000f;
		Float high_lowdiff = 0f;

		Float highdiff;
		Float low_highdiff = 1000000000f;
		Float high_highdiff = 0f;

		Integer Length = low.size();
		Core c = new Core();
		double[] closePrice = new double[5000];
		double[] out = new double[5000];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		ArrayList<Float> lowdiffarr = new ArrayList<Float>();
		ArrayList<Float> highdiffarr = new ArrayList<Float>();

		RetCode retCode = c.wma(0, lowArray.length - 1, lowArray, 50, begin, length, out);
		if (retCode == RetCode.Success) {
			for (int i = begin.value; i < length.value + begin.value; i++) {
				lowdiff = lowArray[i] - Float.valueOf(String.valueOf(out[i - begin.value]));
				// System.out.println(i+":"+lowdiff+";"+lowArray[i]+"::"+out[i-begin.value]);
				lowdiffarr.add(lowdiff);
			}
		}

		retCode = c.wma(0, highArray.length - 1, highArray, 50, begin, length, out);
		if (retCode == RetCode.Success) {
			for (int i = begin.value; i < length.value + begin.value; i++) {
				highdiff = highArray[i] - Float.valueOf(String.valueOf(out[i - begin.value]));

				highdiffarr.add(highdiff);
			}
		}

		float[] highdiffArray = new float[highdiffarr.size()];
		k = 0;

		for (Float f : highdiffarr) {
			highdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever
																// default you
																// want.
		}

		float[] lowdiffArray = new float[lowdiffarr.size()];
		k = 0;

		for (Float f : lowdiffarr) {
			lowdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever
																// default you
																// want.
		}

		// lowdiffArray, highdiffarray, obvArray,lowarray ,closeArrayready

		DecimalFormat df1 = new DecimalFormat("#.##");

		double[] vals = getstochvals(lowdiffArray, highdiffArray, obvArray, lowArray, retCode, c);

		float[] sptnd = getsupertrend(closeArray, highArray, lowArray, c);
		float[] sptnd7 = getsupertrend7(closeArray, highArray, lowArray, c);

		indick = Double.valueOf(df1.format(vals[0]));
		indicd = Double.valueOf(df1.format(vals[1]));
		Double p_indick = Double.valueOf(df1.format(vals[2]));
		Double p_indicd = Double.valueOf(df1.format(vals[3]));
		dayst.setPercentK(indick);
		dayst.setPercentd(indicd);
		dayst.setPrevPercentK(p_indick);
		dayst.setPrevPercentd(p_indicd);
		dayst.setTrend3down(sptnd[1]);
		dayst.setTrend3up(sptnd[0]);
		dayst.setTrend5down(sptnd7[1]);
		dayst.setTrend5up(sptnd7[0]);
		if (sptnd[2] == -1)
			dayst.setTrend3("Down");
		else if (sptnd[2] == 1)
			dayst.setTrend3("Up");
		else
			dayst.setTrend3("NA");

		if (sptnd7[2] == -1)
			dayst.setTrend5("Down");
		else if (sptnd7[2] == 1)
			dayst.setTrend5("Up");
		else
			dayst.setTrend5("NA");
		return dayst;
	}

	private float[] getsupertrend7(float[] closeArray, float[] highArray, float[] lowArray, Core c) {
		// TODO Auto-generated method stub
		double[] out = new double[5000];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		float up = 0, dn = 0;
		float trendup = 0, trenddown = 0, trend = 0, atr = 0;
		for (int i = 0; i < closeArray.length - 2; i++) {
			if (i >= 14) {
				up = ((highArray[i] + lowArray[i]) / 2);
				dn = ((highArray[i] + lowArray[i]) / 2);
				RetCode retCode = c.atr(i - 14, i, highArray, lowArray, closeArray, 14, begin, length, out);
				if (retCode == RetCode.Success) {
					up = (float) (up - (5 * out[length.value - 1]));
					dn = (float) (dn + (5 * out[length.value - 1]));

				}
				if (closeArray[i - 1] > trendup) {
					if (up > trendup)
						trendup = up;
				} else
					trendup = up;

				if (closeArray[i - 1] < trenddown) {
					if (dn < trenddown)
						trenddown = dn;
				} else
					trenddown = dn;

				if (closeArray[i] > trenddown)
					trend = 1;
				if (closeArray[i] < trendup)
					trend = -1;

			} // if end
		}

		return new float[] { trenddown, trendup, trend };
	}

	private float[] getsupertrend(float[] closeArray, float[] highArray, float[] lowArray, Core c) {
		// TODO Auto-generated method stub
		double[] out = new double[5000];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		float up = 0, dn = 0;
		float trendup = 0, trenddown = 0, trend = 0, atr = 0;
		for (int i = 0; i < closeArray.length - 2; i++) {
			if (i >= 14) {
				up = ((highArray[i] + lowArray[i]) / 2);
				dn = ((highArray[i] + lowArray[i]) / 2);
				RetCode retCode = c.atr(i - 14, i, highArray, lowArray, closeArray, 14, begin, length, out);
				if (retCode == RetCode.Success) {
					up = (float) (up - (3 * out[length.value - 1]));
					dn = (float) (dn + (3 * out[length.value - 1]));

				}
				if (closeArray[i - 1] > trendup) {
					if (up > trendup)
						trendup = up;
				} else
					trendup = up;

				if (closeArray[i - 1] < trenddown) {
					if (dn < trenddown)
						trenddown = dn;
				} else
					trenddown = dn;

				if (closeArray[i] > trenddown)
					trend = 1;
				if (closeArray[i] < trendup)
					trend = -1;

			} // if end
		}

		return new float[] { trenddown, trendup, trend };
	}

	private Float getobvvol(Float c_highprice, Float c_lowprice, Float c_closeprice, Float c_currentvolume,
			Float obvvolume, Float closeprice) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		Float range = (c_highprice + c_lowprice) / 2;
		if (c_closeprice >= range) {
			if (c_closeprice >= closeprice) {
				return (float) (obvvolume + c_currentvolume);
			} else {
				return obvvolume;
			}
		} else {
			if (c_closeprice >= closeprice) {
				return obvvolume;
			} else {
				return obvvolume - c_currentvolume;
			}
		}

	}

	private double[] getstochvals(float[] lowdiffArray, float[] highdiffArray, float[] obvArray, float[] lowArray,
			RetCode retCode, Core c) {
		// TODO Auto-generated method stub
		double[] outFastK = new double[5000];
		double[] outFastD = new double[5000];
		MInteger outNBElement = new MInteger();
		MInteger outBegIdx = new MInteger();

		double lowk = 0, lowd = 0, highk = 0, highd = 0, volk = 0, vold = 0, pricek = 0, priced = 0, finalk = 0,
				finald = 0;

		double p_lowk = 0, p_lowd = 0, p_highk = 0, p_highd = 0, p_volk = 0, p_vold = 0, p_pricek = 0, p_priced = 0,
				p_finalk = 0, p_finald = 0;

		/*
		 * double[] outMin = null; double[] outMax = null; retCode = c.minMax(0,
		 * lowdiffArray.length-1, lowdiffArray, 14, outBegIdx, outNBElement,
		 * outMin, outMax);
		 */
		retCode = c.stochF(0, lowdiffArray.length - 1, lowdiffArray, lowdiffArray, lowdiffArray, 14, 3, MAType.Wma,
				outBegIdx, outNBElement, outFastK, outFastD);
		if (retCode == RetCode.Success) {
			try {
				lowk = outFastK[outNBElement.value - 1];
				lowd = outFastD[outNBElement.value - 1];

				p_lowk = outFastK[outNBElement.value - 2];
				p_lowd = outFastD[outNBElement.value - 2];

			} catch (Exception e) {
				lowk = 75;
				lowd = 75;
				p_lowk = 75;
				p_lowd = 75;
			}
			// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
		}

		retCode = c.stochF(0, highdiffArray.length - 1, highdiffArray, highdiffArray, highdiffArray, 14, 3, MAType.Wma,
				outBegIdx, outNBElement, outFastK, outFastD);
		if (retCode == RetCode.Success) {
			try {
				highk = outFastK[outNBElement.value - 1];
				highd = outFastD[outNBElement.value - 1];
				p_highk = outFastK[outNBElement.value - 2];
				p_highd = outFastD[outNBElement.value - 2];
			} catch (Exception e) {
				highk = 75;
				highd = 75;
				p_highk = 75;
				p_highd = 75;
			}
			// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
		}

		retCode = c.stochF(0, obvArray.length - 1, obvArray, obvArray, obvArray, 14, 3, MAType.Wma, outBegIdx,
				outNBElement, outFastK, outFastD);

		if (retCode == RetCode.Success) {
			try {
				volk = outFastK[outNBElement.value - 1];
				vold = outFastD[outNBElement.value - 1];
				p_volk = outFastK[outNBElement.value - 2];
				p_vold = outFastD[outNBElement.value - 2];
			} catch (Exception e) {
				volk = 75;
				vold = 75;
				p_volk = 75;
				p_vold = 75;
			}
			// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
		}

		retCode = c.stochF(0, lowArray.length - 1, lowArray, lowArray, lowArray, 14, 5, MAType.Wma, outBegIdx,
				outNBElement, outFastK, outFastD);

		if (retCode == RetCode.Success) {
			try {
				pricek = outFastK[outNBElement.value - 1];
				priced = outFastD[outNBElement.value - 1];
				p_pricek = outFastK[outNBElement.value - 2];
				p_priced = outFastD[outNBElement.value - 2];
			} catch (Exception e) {
				pricek = 75;
				priced = 75;
				p_pricek = 75;
				p_priced = 75;
			}
			// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
		}

		finalk = (volk + pricek) / 2;
		finald = (vold + ((lowk + lowd + highk + highd + pricek + priced) / 6)) / 2;

		p_finalk = (p_volk + p_pricek) / 2;
		p_finald = (p_vold + ((p_lowk + p_lowd + p_highk + p_highd + p_pricek + p_priced) / 6)) / 2;

		return (new double[] { finalk, finald, p_finalk, p_finald });

	}

	private HashMap<String, StochasticFinalval> getPrevDaystochastic(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Daystochdata> daystoch = new HashMap<String, Daystochdata>();
		HashMap<String, StochasticFinalval> daystochKD = new HashMap<String, StochasticFinalval>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from DAILYSTOCHASTICDATA where tradedate in (select min(tradedate) from (select tradedate from (select distinct tradedate from DAILYSTOCHASTICDATA order by tradedate desc) where rownum < 3))");
		while (rs.next()) {
			Daystochdata dt = new Daystochdata();
			dt.setTradedate(rs.getDate(1));
			dt.setStocksymbol(rs.getString(2));
			dt.setLowdiffstoch(rs.getDouble(3));
			dt.setHighdiffstoch(rs.getDouble(4));
			dt.setPricestoch(rs.getDouble(5));
			dt.setObvstoch(rs.getDouble(6));
			daystoch.put(dt.getStocksymbol(), dt);
		}
		rs.close();
		stmt.close();

		Iterator it = daystoch.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			StochasticFinalval sfv = new StochasticFinalval();
			sfv = getstochasticfinalVal((Daystochdata) pair.getValue(), conn);
			daystochKD.put(sfv.getStocksymbol(), sfv);
		}

		return daystochKD;
	}

	private HashMap<String, Weekpivot> getweekintradaypivot(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Weekpivot> weekpivot = new HashMap<String, Weekpivot>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select stocksymbol,monthclose,monthopen,monthlow,monthhigh from LASTMONTHSTATS ");
		while (rs.next()) {

			String stocksymbol = (rs.getString(1));
			double close = (rs.getDouble(2));
			double open = (rs.getDouble(3));
			double low = (rs.getDouble(4));
			double high = (rs.getDouble(5));

			weekpivot.put(stocksymbol, getpivot(open, high, low, close));
		}
		rs.close();
		stmt.close();
		return weekpivot;
	}

	private Weekpivot getpivot(double open, double high, double low, double close) {
		// TODO Auto-generated method stub
		double pp = (high + low + close) / 3;
		double range = high - low;

		// standard pivot
		/*
		 * double s1 = (2*pp) - high; double s2 = pp-range; double s3 =
		 * s2-range; double s4 = s3-range; double r1 = (2*pp) - low; double r2 =
		 * pp+range; double r3 = r2+range; double r4 = r3+range;
		 */

		// carmilla pivot
		double s1 = close - ((range * 1.1) / 12);
		double s2 = close - ((range * 1.1) / 6);
		double s3 = close - ((range * 1.1) / 4);
		double s4 = close - ((range * 1.1) / 2);
		double r1 = close + ((range * 1.1) / 12);
		double r2 = close + ((range * 1.1) / 6);
		double r3 = close + ((range * 1.1) / 4);
		double r4 = close + ((range * 1.1) / 2);

		Weekpivot wp = new Weekpivot();
		wp.setPP(pp);
		wp.setS1(s1);
		wp.setS2(s2);
		wp.setS3(s3);
		wp.setS4(s4);
		wp.setR1(r1);
		wp.setR2(r2);
		wp.setR3(r3);
		wp.setR4(r4);

		return wp;
	}

	private Daypivot getpivot1(double open, double high, double low, double close) {
		// TODO Auto-generated method stub
		double pp = (high + low + close) / 3;
		double range = high - low;
		// standard pivot
		/*
		 * double s1 = (2*pp) - high; double s2 = pp-range; double s3 =
		 * s2-range; double s4 = s3-range; double r1 = (2*pp) - low; double r2 =
		 * pp+range; double r3 = r2+range; double r4 = r3+range;
		 */

		double s1 = close - ((range * 1.1) / 12);
		double s2 = close - ((range * 1.1) / 6);
		double s3 = close - ((range * 1.1) / 4);
		double s4 = close - ((range * 1.1) / 2);
		double r1 = close + ((range * 1.1) / 12);
		double r2 = close + ((range * 1.1) / 6);
		double r3 = close + ((range * 1.1) / 4);
		double r4 = close + ((range * 1.1) / 2);

		// Demark pivot
		/*
		 * //S2-4,R2-4 fibonaaci interpretation double x=0; if (close < open) x
		 * = high + (2 * low) + close; else if (close > open) x = low + (2 *
		 * high) + close; else x = high+low+(2*close);
		 * 
		 * double s1 = (x/2) -high; double r1 = (x/2) - low; double diff = r1
		 * -s1; double s2 = s1 -(diff*0.272); double s3 = s1 -(diff*0.618);
		 * double s4 = s1 -(diff);
		 * 
		 * double r2 = r1 +(diff*0.272); double r3 = r1 +(diff*0.618); double r4
		 * = r1+diff;
		 * 
		 */
		Daypivot wp = new Daypivot();
		wp.setPP(pp);
		wp.setS1(s1);
		wp.setS2(s2);
		wp.setS3(s3);
		wp.setS4(s4);
		wp.setR1(r1);
		wp.setR2(r2);
		wp.setR3(r3);
		wp.setR4(r4);

		return wp;
	}

	private HashMap<String, StockOtherTechnicals> getnse100(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, StockOtherTechnicals> nse200 = new HashMap<String, StockOtherTechnicals>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select STOCKSYMBOL from nse100");
		// Hourdatadownloader hd = new Hourdatadownloader();
		while (rs.next()) {
			// nse200.put(rs.getString(1), hd.getstochdata(rs.getString(1)));
			nse200.put(rs.getString(1), new StockOtherTechnicals());

		}
		rs.close();
		stmt.close();
		return nse200;
	}

	private Object getWeekstochasticnew(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Stocktwoweekstat> twoweekstat = gettwoweekstats(conn);

		return twoweekstat;

	}

	private Object getEODWR(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, StockOtherTechnicals> othertech = new HashMap<String, StockOtherTechnicals>();
		DecimalFormat df = new DecimalFormat("#.##");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from eodwr");
		while (rs.next()) {
			StockOtherTechnicals st = new StockOtherTechnicals();
			st.setStocksymbol(rs.getString(1));
			st.setDaywilliamsr(Double.valueOf(df.format(rs.getDouble(2))));
			st.setWeekwilliamsr(Double.valueOf(df.format(rs.getDouble(3))));

			othertech.put(st.getStocksymbol(), st);

		}
		rs.close();
		stmt.close();
		return othertech;
	}

	private Object getWeek52HL(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, HistoricHighLow> HistHL = new HashMap<String, HistoricHighLow>();
		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery("select * from week52highlow");
		while (rs1.next()) {
			HistoricHighLow HL = new HistoricHighLow();
			HL.setStocksymbol(rs1.getString(1));
			HL.setHighprice(rs1.getDouble(2));
			HL.setLowprice(rs1.getDouble(3));
			HistHL.put(HL.getStocksymbol(), HL);
		}
		stmt1.close();
		rs1.close();
		return HistHL;
	}

	private Object getHistoricHL(Connection conn) throws SQLException {
		// TODO Auto-generated method stub

		HashMap<String, HistoricHighLow> HistHL = new HashMap<String, HistoricHighLow>();
		Statement stmt1 = conn.createStatement();
		ResultSet rs1 = stmt1.executeQuery("select * from historichighlow");
		while (rs1.next()) {
			HistoricHighLow HL = new HistoricHighLow();
			HL.setStocksymbol(rs1.getString(1));
			HL.setHighdate(rs1.getDate(2));
			HL.setLowdate(rs1.getDate(3));
			HL.setHighprice(rs1.getDouble(4));
			HL.setLowprice(rs1.getDouble(5));
			HistHL.put(HL.getStocksymbol(), HL);
		}
		stmt1.close();
		rs1.close();
		return HistHL;
	}

	private HashMap<String, WeekstochasticFinalval> getWeekstochastic(Connection conn) throws SQLException {
		// TODO Auto-generated method stub

		HashMap<String, Weekstochdata> daystoch = new HashMap<String, Weekstochdata>();
		HashMap<String, WeekstochasticFinalval> daystochKD = new HashMap<String, WeekstochasticFinalval>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from WEEKLYSTOCHASTICDATA where tradedate in (select max(tradedate) from WEEKLYSTOCHASTICDATA)");
		while (rs.next()) {
			Weekstochdata dt = new Weekstochdata();
			dt.setTradedate(rs.getDate(1));
			dt.setStocksymbol(rs.getString(2));
			dt.setLowdiffstoch(rs.getDouble(3));
			dt.setHighdiffstoch(rs.getDouble(4));
			dt.setPricestoch(rs.getDouble(5));
			dt.setObvstoch(rs.getDouble(6));
			dt.setTradeyr(rs.getString(7));
			dt.setTradeweek(rs.getString(8));
			daystoch.put(dt.getStocksymbol(), dt);
		}
		rs.close();
		stmt.close();

		Iterator it = daystoch.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			WeekstochasticFinalval sfv = new WeekstochasticFinalval();
			sfv = getweekstochasticfinalVal((Weekstochdata) pair.getValue(), conn);
			daystochKD.put(sfv.getStocksymbol(), sfv);
		}

		return daystochKD;

	}

	private WeekstochasticFinalval getweekstochasticfinalVal(Weekstochdata daystoch, Connection conn)
			throws SQLException {
		// TODO Auto-generated method stub
		Float WMAlowdiffstoch = (float) 0;
		Float WMAhighdiffstoch = (float) 0;
		Float WMApricestoch = (float) 0;
		Float WMAobvvol = (float) 0;

		PreparedStatement stmt = conn.prepareStatement(
				"select * from ((select lowdiffstoch,highdiffstoch,obvstoch from WEEKLYSTOCHASTICDATA where stocksymbol= ? order by tradedate desc)) where rownum < 4",
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1, daystoch.getStocksymbol());

		ResultSet rs1 = stmt.executeQuery();
		rs1.last();
		int rows = rs1.getRow();
		if (rows == 3) {
			rs1.beforeFirst();
			Float i = (float) 3;
			while (rs1.next()) {
				WMAlowdiffstoch = (float) (WMAlowdiffstoch + ((i / 6) * rs1.getDouble(1)));
				WMAhighdiffstoch = (float) (WMAhighdiffstoch + ((i / 6) * rs1.getDouble(2)));
				WMAobvvol = (float) (WMAobvvol + ((i / 6) * rs1.getDouble(3)));
				i--;

			}
		}
		rs1.close();
		stmt.close();

		PreparedStatement stmt1 = conn.prepareStatement(
				"select * from ((select pricestoch from WEEKLYSTOCHASTICDATA where stocksymbol= ? order by tradedate desc)) where rownum < 6",
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt1.setString(1, daystoch.getStocksymbol());

		ResultSet rs2 = stmt1.executeQuery();
		rs2.last();
		int irows = rs2.getRow();
		if (irows == 5) {
			rs2.beforeFirst();
			Float j = (float) 5;
			while (rs2.next()) {

				WMApricestoch = (float) (WMApricestoch + ((j / 15) * rs2.getDouble(1)));
				j--;

			}
		}
		rs2.close();
		stmt1.close();

		double val = (daystoch.getPricestoch() + daystoch.getLowdiffstoch() + daystoch.getHighdiffstoch()
				+ WMApricestoch + WMAlowdiffstoch + WMAhighdiffstoch) / 6;

		double finalk = (daystoch.getPricestoch() + daystoch.getObvstoch()) / 2;
		double finald = (val + WMAobvvol) / 2;

		WeekstochasticFinalval sfv = new WeekstochasticFinalval();
		sfv.setTradedate(daystoch.getTradedate());
		sfv.setStocksymbol(daystoch.getStocksymbol());
		sfv.setPrecentk(finalk);
		sfv.setPercentd(finald);
		sfv.setTradeyr(daystoch.getTradeyr());
		sfv.setTradeweek(daystoch.getTradeweek());

		System.out.println(
				sfv.getStocksymbol() + "::" + sfv.getTradedate() + "::" + sfv.getPrecentk() + "::" + sfv.getPercentd());
		return sfv;
	}

	private HashMap<String, StochasticFinalval> getDaystochastic(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Daystochdata> daystoch = new HashMap<String, Daystochdata>();
		HashMap<String, StochasticFinalval> daystochKD = new HashMap<String, StochasticFinalval>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from DAILYSTOCHASTICDATA where tradedate in (select max(tradedate) from DAILYSTOCHASTICDATA)");
		while (rs.next()) {
			Daystochdata dt = new Daystochdata();
			dt.setTradedate(rs.getDate(1));
			dt.setStocksymbol(rs.getString(2));
			dt.setLowdiffstoch(rs.getDouble(3));
			dt.setHighdiffstoch(rs.getDouble(4));
			dt.setPricestoch(rs.getDouble(5));
			dt.setObvstoch(rs.getDouble(6));
			daystoch.put(dt.getStocksymbol(), dt);
		}
		rs.close();
		stmt.close();

		Iterator it = daystoch.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			StochasticFinalval sfv = new StochasticFinalval();
			sfv = getstochasticfinalVal((Daystochdata) pair.getValue(), conn);
			daystochKD.put(sfv.getStocksymbol(), sfv);
		}

		return daystochKD;
	}

	private StochasticFinalval getstochasticfinalVal(Daystochdata daystoch, Connection conn) throws SQLException

	{

		Float WMAlowdiffstoch = (float) 0;
		Float WMAhighdiffstoch = (float) 0;
		Float WMApricestoch = (float) 0;
		Float WMAobvvol = (float) 0;

		PreparedStatement stmt = conn.prepareStatement(
				"select * from ((select lowdiffstoch,highdiffstoch,obvstoch from DAILYSTOCHASTICDATA where stocksymbol= ? order by tradedate desc)) where rownum < 4",
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt.setString(1, daystoch.getStocksymbol());

		ResultSet rs1 = stmt.executeQuery();
		rs1.last();
		int rows = rs1.getRow();
		if (rows == 3) {
			rs1.beforeFirst();
			Float i = (float) 3;
			while (rs1.next()) {
				WMAlowdiffstoch = (float) (WMAlowdiffstoch + ((i / 6) * rs1.getDouble(1)));
				WMAhighdiffstoch = (float) (WMAhighdiffstoch + ((i / 6) * rs1.getDouble(2)));
				WMAobvvol = (float) (WMAobvvol + ((i / 6) * rs1.getDouble(3)));
				i--;

			}
		}
		rs1.close();
		stmt.close();

		PreparedStatement stmt1 = conn.prepareStatement(
				"select * from ((select pricestoch from DAILYSTOCHASTICDATA where stocksymbol= ? order by tradedate desc)) where rownum < 6",
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt1.setString(1, daystoch.getStocksymbol());

		ResultSet rs2 = stmt1.executeQuery();
		rs2.last();
		int irows = rs2.getRow();
		if (irows == 5) {
			rs2.beforeFirst();
			Float j = (float) 5;
			while (rs2.next()) {

				WMApricestoch = (float) (WMApricestoch + ((j / 15) * rs2.getDouble(1)));
				j--;

			}
		}
		rs2.close();
		stmt1.close();

		double val = (daystoch.getPricestoch() + daystoch.getLowdiffstoch() + daystoch.getHighdiffstoch()
				+ WMApricestoch + WMAlowdiffstoch + WMAhighdiffstoch) / 6;

		double finalk = (daystoch.getPricestoch() + daystoch.getObvstoch()) / 2;
		double finald = (val + WMAobvvol) / 2;

		StochasticFinalval sfv = new StochasticFinalval();
		sfv.setTradedate(daystoch.getTradedate());
		sfv.setStocksymbol(daystoch.getStocksymbol());
		sfv.setPrecentk(finalk);
		sfv.setPercentd(finald);

		// System.out.println(sfv.getStocksymbol()+"::"+sfv.getTradedate()+"::"+sfv.getPrecentk()+"::"+sfv.getPercentd());
		return sfv;

	}

	private HashMap<String, Daypivot> getDaypivot(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Daypivot> daypivot = new HashMap<String, Daypivot>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("select stocksymbol,monthclose,monthopen,monthlow,monthhigh from LASTMONTHSTATS ");
		while (rs.next()) {

			String stocksymbol = (rs.getString(1));
			double close = (rs.getDouble(2));
			double open = (rs.getDouble(3));
			double low = (rs.getDouble(4));
			double high = (rs.getDouble(5));

			daypivot.put(stocksymbol, getpivot1(open, high, low, close));
		}
		rs.close();
		stmt.close();
		return daypivot;
	}

	private Object getDaysma50(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Daysma50> weeksma50 = new HashMap<String, Daysma50>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select STOCKSYMBOL,MOVING_AVERAGE from DAYSMA50");
		while (rs.next()) {
			Daysma50 wm50 = new Daysma50();
			wm50.setStocksymbol(rs.getString(1));

			wm50.setMovingavg(rs.getDouble(2));
			weeksma50.put(rs.getString(1), wm50);
		}
		rs.close();
		stmt.close();
		return weeksma50;
	}

	private Object gettwodaystats(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Stocktwodaystat> twodaystat = new HashMap<String, Stocktwodaystat>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from TWODAYSTATS");
		while (rs.next()) {
			Stocktwodaystat sw2 = new Stocktwodaystat();
			sw2.setSTOCKSYMBOL(rs.getString(1));
			sw2.setPREV_OPEN(rs.getDouble(3));
			sw2.setPREV_HIGH(rs.getDouble(4));
			sw2.setPREV_LOW(rs.getDouble(5));
			sw2.setPREV_CLOSE(rs.getDouble(6));

			sw2.setCURR_OPEN(rs.getDouble(9));
			sw2.setCURR_HIGH(rs.getDouble(10));
			sw2.setCURR_LOW(rs.getDouble(11));
			sw2.setCURR_CLOSE(rs.getDouble(12));

			twodaystat.put(rs.getString(1), sw2);
		}
		rs.close();
		stmt.close();
		return twodaystat;

	}

	private Object gettwomonthstats(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Stocktwomonthstat> twomonthstat = new HashMap<String, Stocktwomonthstat>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from LASTTWOMONTHSTATS");
		while (rs.next()) {
			Stocktwomonthstat sw2 = new Stocktwomonthstat();
			sw2.setSTOCKSYMBOL(rs.getString(1));
			sw2.setPREV_OPEN(rs.getDouble(2));
			sw2.setPREV_HIGH(rs.getDouble(3));
			sw2.setPREV_LOW(rs.getDouble(4));
			sw2.setPREV_CLOSE(rs.getDouble(5));

			sw2.setCURR_OPEN(rs.getDouble(6));
			sw2.setCURR_HIGH(rs.getDouble(7));
			sw2.setCURR_LOW(rs.getDouble(8));
			sw2.setCURR_CLOSE(rs.getDouble(9));

			twomonthstat.put(rs.getString(1), sw2);
		}
		rs.close();
		stmt.close();
		return twomonthstat;

	}

	private HashMap<String, String> getnse200(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, String> nse200 = new HashMap<String, String>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select STOCKSYMBOL from nse200");
		while (rs.next()) {

			nse200.put(rs.getString(1), "");
		}
		rs.close();
		stmt.close();
		return nse200;
	}

	private HashMap<String, Weeksma50> getweeksma50(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Weeksma50> weeksma50 = new HashMap<String, Weeksma50>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select STOCKSYMBOL,TYRTWEEK,MOVING_AVERAGE from weeksma50");
		while (rs.next()) {
			Weeksma50 wm50 = new Weeksma50();
			wm50.setStocksymbol(rs.getString(1));
			wm50.setTradeyyww(rs.getInt(2));
			wm50.setMovingavg(rs.getDouble(3));
			weeksma50.put(rs.getString(1), wm50);
		}
		rs.close();
		stmt.close();
		return weeksma50;

	}

	private HashMap<String, Stocktwoweekstat> gettwoweekstats(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Stocktwoweekstat> twoweekstat = new HashMap<String, Stocktwoweekstat>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from LASTTWOWEEKS");
		while (rs.next()) {
			Stocktwoweekstat sw2 = new Stocktwoweekstat();
			sw2.setSTOCKSYMBOL(rs.getString(1));
			sw2.setPREV_CLOSE(rs.getDouble(2));
			sw2.setPREV_OPEN(rs.getDouble(3));
			sw2.setPREV_LOW(rs.getDouble(4));
			sw2.setPREV_HIGH(rs.getDouble(5));
			sw2.setPREV_TRADEVOLUME(rs.getLong(6));
			sw2.setPREV_TRADEYR(rs.getInt(7));
			sw2.setPREV_TRADEWEEK(rs.getInt(8));
			sw2.setPREV_STARTDATE(rs.getDate(9));
			sw2.setPREV_ENDATE(rs.getDate(10));
			sw2.setCURR_CLOSE(rs.getDouble(11));
			sw2.setCURR_OPEN(rs.getDouble(12));
			sw2.setCURR_LOW(rs.getDouble(13));
			sw2.setCURR_HIGH(rs.getDouble(14));
			sw2.setCURR_TRADEVOLUME(rs.getLong(15));
			sw2.setCURR_TRADEYR(rs.getInt(16));
			sw2.setCURR_TRADEWEEK(rs.getInt(17));
			sw2.setCURR_STARTDATE(rs.getDate(18));
			sw2.setCURR_ENDATE(rs.getDate(19));
			twoweekstat.put(rs.getString(1), sw2);
		}
		rs.close();
		stmt.close();
		return twoweekstat;

	}

	private HashMap<String, Weekpivot> getweekpivot(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Weekpivot> daypivot = new HashMap<String, Weekpivot>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select stocksymbol,yearclose,yearopen,yearlow,yearhigh from YearStats_2016");
		while (rs.next()) {

			String stocksymbol = (rs.getString(1));
			double close = (rs.getDouble(2));
			double open = (rs.getDouble(3));
			double low = (rs.getDouble(4));
			double high = (rs.getDouble(5));

			daypivot.put(stocksymbol, getpivotyr(open, high, low, close));
		}
		rs.close();
		stmt.close();
		return daypivot;

	}

	private Weekpivot getpivotyr(double open, double high, double low, double close) {
		// TODO Auto-generated method stub
		double pp = (high + low + close) / 3;
		double range = high - low;
		// standard pivot
		/*
		 * double s1 = (2*pp) - high; double s2 = pp-range; double s3 =
		 * s2-range; double s4 = s3-range; double r1 = (2*pp) - low; double r2 =
		 * pp+range; double r3 = r2+range; double r4 = r3+range;
		 */

		// carmilla pivot

		double s1 = close - ((range * 1.1) / 12);
		double s2 = close - ((range * 1.1) / 6);
		double s3 = close - ((range * 1.1) / 4);
		double s4 = close - ((range * 1.1) / 2);
		double r1 = close + ((range * 1.1) / 12);
		double r2 = close + ((range * 1.1) / 6);
		double r3 = close + ((range * 1.1) / 4);
		double r4 = close + ((range * 1.1) / 2);

		// camarilla changes:-

		s1 = s3;
		s2 = s4;
		s3 = s2 - (s1 - s2);
		s4 = s3 - (s1 - s2);

		// Demark pivot
		// S2-4,R2-4 fibonaaci interpretation
		/*
		 * double x=0; if (close < open) x = high + (2 * low) + close; else if
		 * (close > open) x = low + (2 * high) + close; else x =
		 * high+low+(2*close);
		 * 
		 * 
		 * double s3 = (x/2) -high; double r1 = (x/2) - low; double diff = r1
		 * -s3; double s4 = s3 -(diff*0.272); double s2 = r1 -(diff*0.786);
		 * double s1 = r1 -(diff*0.618);
		 * 
		 * double r2 = r1 +(diff*0.272); double r3 = r1 +(diff*0.618); double r4
		 * = r1+diff;
		 */

		Weekpivot wp = new Weekpivot();
		wp.setPP(pp);
		wp.setS1(s1);
		wp.setS2(s2);
		wp.setS3(s3);
		wp.setS4(s4);
		wp.setR1(r1);
		wp.setR2(r2);
		wp.setR3(r3);
		wp.setR4(r4);

		return wp;
	}

}
