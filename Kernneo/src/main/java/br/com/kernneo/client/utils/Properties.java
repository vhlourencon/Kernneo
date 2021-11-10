package br.com.kernneo.client.utils;

import br.com.kernneo.client.types.Plataforma;
import br.com.kernneo.client.types.Resolucao;

public class Properties {
    public static final String IP_porta_SERVIDOR = "10.15.4.11:8080";
    public static final String PACOTE = "br.com.kernneo.PrincipalWeb";
    public static final String MODULO = "erp";
    private static boolean dev = true;
    private static Plataforma plataforma = Plataforma.android;
    private static Resolucao resolucao = Resolucao.alta;
    
   
    private static String DIR_CONFIG = "c:\\kernneo\\" ; 

    public static String NOME_RESTAURANTE =  "GOVERNO DE MATO GROSSO";
    public static String DIR_IMAGENS_USUARIOS = "imagens_usuario";
    public static String DIR_IMAGENS_CATEGORIAS = "imagens_categoria";
    public static String DIR_IMAGENS_PRODUTOS = "imagens_produto";
    public static String DIR_SPED = "sped";

    public static String URL_IMAGENS_USUARIOS = "http://" + IP_porta_SERVIDOR + "/" + DIR_IMAGENS_USUARIOS + "/";
    public static String URL_IMAGENS_CATEGORIA = "http://" + IP_porta_SERVIDOR + "/" + DIR_IMAGENS_CATEGORIAS + "/";
    public static String URL_IMAGENS_PRODUTO = "http://" + IP_porta_SERVIDOR + "/" + DIR_IMAGENS_PRODUTOS + "/";

    public static boolean isDev() {
	return dev;
    }
    
    
    
    

    public static void setDev(boolean dev) {
	Properties.dev = dev;
    }

    public static Plataforma getPlataforma() {
	return plataforma;
    }

    public static void setPlataforma(Plataforma plataforma) {
	Properties.plataforma = plataforma;
    }
  




    public static String getDIR_CONFIG() {
	return DIR_CONFIG;
    }





    public static void setDIR_CONFIG(String dIR_CONFIG) {
	DIR_CONFIG = dIR_CONFIG;
    }





    public static Resolucao getResolucao() {
	return resolucao;
    }





    public static void setResolucao(Resolucao resolucao) {
	Properties.resolucao = resolucao;
    }

}
