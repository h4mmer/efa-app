/*     */ package com.example.efa.logic;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ 
/*     */ public class Curl
/*     */ {
/*     */   private LogMe log;
/*     */ 
/*     */   public Curl(int i)
/*     */   {
/*  22 */     File file = new File("fbuserids.txt");
/*  23 */     this.log = new LogMe(file);
/*  24 */     if (!file.exists()) {
/*  25 */       System.out.println("created new File");
/*     */     }
/*  27 */     logids(i);
/*     */   }
/*     */ 
/*     */   public Curl() {
/*  31 */     File file = new File("fbuserids.txt");
/*  32 */     this.log = new LogMe(file);
/*  33 */     if (!file.exists())
/*  34 */       System.out.println("created new File");
/*     */   }
/*     */ 
/*     */   private String log_valid_ids(String s)
/*     */     throws UnsupportedEncodingException, IOException
/*     */   {
/*  41 */     BufferedReader reader = get_url("https://graph.facebook.com/" + s + 
/*  42 */       "?fields=id&method=GET");
/*     */     String line;
/*  43 */     while ((line = reader.readLine()) != null)
/*     */     {
/*     */       
/*  44 */       if (line.contains("id")) {
/*  45 */         s = line.substring(7, line.lastIndexOf('"'));
/*  46 */         System.out.println("ID: " + s);
/*     */       }
/*     */       else {
/*  49 */         s = null;
/*     */       }
/*     */     }
/*     */ 
/*  53 */     this.log.schreiben(s);
/*  54 */     return s;
/*     */   }
/*     */ 
/*     */   private BufferedReader get_url(String s) throws UnsupportedEncodingException, IOException
/*     */   {
/*  59 */     URL url = new URL(s);
/*  60 */     BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
/*  61 */     return reader;
/*     */   }
/*     */ 
/*     */   public void logids()
/*     */   {
/*  67 */     long id = 0L;
/*     */     try {
/*  69 */       id = Long.parseLong(this.log.getLastline()) + 1L;
/*     */     } catch (NumberFormatException e2) {
/*  71 */       System.out.println("ID ist zu groß: maximal 2.147.483.647");
/*     */     }
/*     */     catch (IOException e2) {
/*  74 */       e2.printStackTrace();
/*     */     }
/*  76 */     logids(id);
/*     */   }
/*     */   public void logids(long id) {
/*  79 */     logids(id, 1);
/*     */   }
/*     */ 
/*     */   public void logids(long id, int anzahl)
/*     */   {
/*  84 */     while (anzahl > 0) {
/*  85 */       String sid = String.valueOf(id);
/*     */       try {
/*  87 */         log_valid_ids(sid);
/*  88 */         id += 1L;
/*  89 */         anzahl--;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  93 */         String s = e.toString();
/*  94 */         if (s.contains("Server returned HTTP response code: 400")) {
/*  95 */           id += 1L;
/*     */         }
/*  97 */         else if (s.contains("Server returned HTTP response code: 403"))
/*     */           try {
/*  99 */             long time = System.currentTimeMillis();
/* 100 */             String error = null;
/*     */             do {
/* 102 */               Thread.sleep(300010L);
/* 103 */               System.out.println("Es muss 5 min gewartet werden");
/*     */               try {
/* 105 */                 log_valid_ids(sid);
/* 106 */                 error = null;
/*     */               } catch (IOException e1) {
/* 108 */                 error = e1.toString();
/* 109 */                 if (s.contains("Server returned HTTP response code: 400"))
/* 110 */                   error = null; 
/*     */               }
/*     */             }
/* 112 */             while (error != null);
/* 113 */             System.out.println("Breaktime: " + (
/* 114 */               System.currentTimeMillis() - time) + "ms");
/*     */           } catch (InterruptedException e1) {
/* 116 */             e1.printStackTrace();
/*     */           }
/*     */         else
/* 119 */           e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */  
/*     */ 
/*     */   public int vote(String fbuserid, int projectid) throws IOException
/*     */   {
/* 151 */     int back = 0;
/* 152 */     String body = "fbuserid=" + 
/* 153 */       URLEncoder.encode(String.valueOf(fbuserid), "UTF-8") + "&" + 
/* 154 */       "projectid=" + 
/* 155 */       URLEncoder.encode(String.valueOf(projectid), "UTF-8");
/*     */ 
/* 157 */     URL url = new URL(
/* 158 */       "https://facebook.digitalroyal.de/skfoerde/abi2013/abstimmen/setvoting.php");
/* 159 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 160 */     connection.setRequestMethod("POST");
/* 161 */     connection.setDoInput(true);
/* 162 */     connection.setDoOutput(true);
/* 163 */     connection.setUseCaches(false);
/* 164 */     connection.setRequestProperty("Content-Type", 
/* 165 */       "application/x-www-form-urlencoded");
/* 166 */     connection.setRequestProperty("Content-Length", 
/* 167 */       String.valueOf(body.length()));
/*     */ 
/* 169 */     OutputStreamWriter writer = new OutputStreamWriter(
/* 170 */       connection.getOutputStream());
/* 171 */     writer.write(body);
/* 172 */     writer.flush();
/*     */ 
/* 174 */     BufferedReader reader = new BufferedReader(
/* 175 */       new InputStreamReader(connection.getInputStream()));
/*     */     String line;
/* 177 */     while ((line = reader.readLine()) != null)
/*     */     {
/*     */       
/* 179 */       if (line.contains("1"))
/*     */       {
/* 181 */         back = 1;
/*     */       }
/*     */       else {
/* 184 */         back = 0;
/*     */       }
/*     */     }
/*     */ 
/* 188 */     writer.close();
/* 189 */     reader.close();
/* 190 */     return back;
/*     */   }
/*     */ 
/*     */   public void logreset() {
/* 194 */     this.log.rest();
/*     */   }
/*     */   public boolean is_id_valid(String id) {
/*     */     try {
/* 198 */       log_valid_ids(id);
/* 199 */       return true; } catch (IOException e) {
/*     */     }
/* 201 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Acer\Downloads\APK-Multi-Toolv1.0.9\APK-Multi-Tool\place-apk-here-for-modding\fbhitfacer_alpha.jar
 * Qualified Name:     logic.Curl
 * JD-Core Version:    0.6.0
 */