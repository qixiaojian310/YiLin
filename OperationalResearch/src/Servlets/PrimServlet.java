package Servlets;

import Model.Graph;
import Prim.Prim;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/**
 * @author  Li Xingdong
 * @since 2020.12.6
 */
@WebServlet("/primServlet")
public class PrimServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String names = request.getParameter("names");
        String list = request.getParameter("list");
        String start = request.getParameter("start");
        if (list==null || names==null || start==null){
            response.sendRedirect(request.getContextPath()+"/index.html");
            return;
        }
        String rawNames = names.substring(1,names.length()-1);
        String[] splitNames = rawNames.split(",");
        for (int i = 0; i < splitNames.length; i++) {
            splitNames[i]=splitNames[i].replaceAll("\"","");
        }
        String rawData = list.substring(1,list.length()-1);
        String[] split = rawData.split(",");
        double[] data = new double[splitNames.length*splitNames.length];
        for (int i = 0; i < data.length; i++) {
            data[i]=Double.parseDouble(split[i]);
        }
        Graph graph = new Graph(splitNames, data);
        Prim prim = new Prim(graph);
        prim.prim(Integer.parseInt(start));
        Map<String,Object> map = new HashMap<>();
        map.put("mediums",prim.getMediums());
        map.put("totalWeight",prim.getTotalWeight());
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),map);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+"/index.html");
    }
}
