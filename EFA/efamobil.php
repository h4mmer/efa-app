<?php

  // Erfassung von Fahrtenbucheinträgen
  // für das elektronische Fahrtenbuch efa über eine Internetseite
  // 
  // Das Skript sammelt die Eingaben in einer Datei efa.records
  // und erstellt eine xml-Importdatei für die Übernahme in efa
  // 
  // Wenn die Importdatei von Webserver für den Import heruntergeladen wird,
  // sollten die Dateien efa_mobil_import.xml und efa.records auf dem Webserver
  // gelöscht werden, damit keine Fahrtenbucheinträge doppelt importiert werden.
  //
  // Itzehoer Wasser-Wanderer e.V.
  // Martin Ölscher
  // mail 1.vorsitzender@itzehoer-wasser-wanderer.de
  // erstellt: 10.08.2012
  // letzte Änderung; 12.12.2012

  $Verein = "Itzehoer-Wasser-Wanderer e.V."

  $Version = "1.2.0";

  // Parameterübernahme
  $bKeepValues = false;
  
  $Name = array();

  $Steuermann = "";
  $TrommlerPos = "";
  $Boot = "";
  $Datum = "";
  $StartZeit = "";
  $EndeZeit = "";
  $Strecke = "";
  $km = "";
  $Bemerkung = "";
  $Fahrtart = "";

  $cell_padding = 2;
   
  if (isset($_POST["cbKeepValues"]))
    $bKeepValues = ("1" == $_POST["cbKeepValues"]);	 
 
  if (isset($_POST["dfBoot"]))
    $Boot = htmlspecialchars($_POST["dfBoot"]);

  if (isset($_POST["dfDatum"]))
    $Datum = htmlspecialchars($_POST["dfDatum"]);

  if (isset($_POST["dfStartZeit"]))
    $StartZeit = htmlspecialchars($_POST["dfStartZeit"]);

  if (isset($_POST["dfEndeZeit"]))
    $EndeZeit = htmlspecialchars($_POST["dfEndeZeit"]);

  if (isset($_POST["dfStrecke"]))
    $Strecke = htmlspecialchars($_POST["dfStrecke"]);

  if (isset($_POST["dfkm"]))
    $km = htmlspecialchars($_POST["dfkm"]);

  if (isset($_POST["dfBemerkung"]))
   $Bemerkung = htmlspecialchars($_POST["dfBemerkung"]);

  $CrewCount = 0;

  if (isset($_POST["dfSteuermann"]))
  {
    $Steuermann = htmlspecialchars($_POST["dfSteuermann"]);
    $CrewCount = $CrewCount + 1;
  }

  for ($i=1; $i<=24; $i++)
  {
    if (isset($_POST["dfName$i"]))
    {
      $Name[$i] = htmlspecialchars($_POST["dfName$i"]);
      $CrewCount = $CrewCount + 1;
    }
    else
      $Name[$i] = "";
  }

  if (isset($_POST["cmbTrommler"]))
    $TrommlerPos = htmlspecialchars($_POST["cmbTrommler"]);
  
  if (isset($_POST["cmbFahrtart"]))
    $Fahrtart = htmlspecialchars($_POST["cmbFahrtart"]);

  // Speichern

  if ($CrewCount > 0)
  {
    if (($Boot != "") and ($Datum != "") and ($StartZeit != "") and ($EndeZeit != "") and ($Strecke != "") and ($km != ""))
    {

      // erstelle xml-Struktur

      $xml_Header = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n<export type=\"text\">\r\n";
      $xml_Footer = "</export>\r\n";

      // Attributliste ohne <BoatCaptain></BoatCaptain>
      // <BoatCaptain></BoatCaptain> darf nicht leer sein. Das Attribut wird inzugefügt, wenn ein Wert
      //in der Auswahlliste cmbTrommler erfasst wurde

      $xml_Record = "<Record><EntryId></EntryId><Date></Date><EndDate></EndDate><Boat></Boat><BoatVariant>1</BoatVariant><Cox></Cox><Crew1></Crew1><Crew2></Crew2><Crew3></Crew3><Crew4></Crew4><Crew5></Crew5><Crew6></Crew6><Crew7></Crew7><Crew8></Crew8><Crew9></Crew9><Crew10></Crew10><Crew11></Crew11><Crew12></Crew12><Crew13></Crew13><Crew14></Crew14><Crew15></Crew15><Crew16></Crew16><Crew17></Crew17><Crew18></Crew18><Crew19></Crew19><Crew20></Crew20><Crew21></Crew21><Crew22></Crew22><Crew23></Crew23><Crew24></Crew24><StartTime></StartTime><EndTime></EndTime><Destination></Destination><WatersList></WatersList><Distance></Distance><Comments></Comments><SessionType></SessionType><SessionGroup></SessionGroup></Record>\r\n";

      $xml_Record = str_ireplace("<Date></Date>","<Date>$Datum</Date>",$xml_Record);
      $xml_Record = str_ireplace("<Boat></Boat>","<Boat>$Boot</Boat>",$xml_Record);
      $xml_Record = str_ireplace("<Cox></Cox>","<Cox>$Steuermann</Cox>",$xml_Record);

      for ($i=1; $i<=24; $i++)
        $xml_Record = str_ireplace("<Crew$i></Crew$i>","<Crew$i>".$Name[$i]."</Crew$i>",$xml_Record);

      // das Attribut <BoatCaptain></BoatCaptain> einfügen, wenn ein Wert erfasst wurde
      if ($TrommlerPos != "")
        $xml_Record = str_ireplace("</Crew24><StartTime>", "</Crew24><BoatCaptain>$TrommlerPos</BoatCaptain><StartTime>", $xml_Record);

      $xml_Record = str_ireplace("<StartTime></StartTime>","<StartTime>$StartZeit</StartTime>",$xml_Record);
      $xml_Record = str_ireplace("<EndTime></EndTime>","<EndTime>$EndeZeit</EndTime>",$xml_Record);
      $xml_Record = str_ireplace("<Destination></Destination>","<Destination>$Strecke</Destination>",$xml_Record);
      $xml_Record = str_ireplace("<Distance></Distance>","<Distance>$km km</Distance>",$xml_Record);
      $xml_Record = str_ireplace("<Comments></Comments>","<Comments>$Bemerkung</Comments>",$xml_Record);
      $xml_Record = str_ireplace("<SessionType></SessionType>","<SessionType>$Fahrtart</SessionType>",$xml_Record);

      // Datensatz in Datei schreiben...
      $FileName = "efa.records";
      $hFile = fopen($FileName,"a+");
      fwrite($hFile, $xml_Record);
      fclose($hFile);

      $xml = file_get_contents($FileName);

      // Importdatei für efa erstellen...
      $FileName = "efa_mobil_import.xml";
      $hFile = fopen($FileName,"w+");
      $xml = $xml_Header.$xml.$xml_Footer;
      fwrite($hFile, $xml);
      fclose($hFile);

    } // if (($Boot != "") and ($Datum != "") and ($StartZeit != "") and ($EndeZeit != "") and ($Strecke != "") and ($km != ""))

  } // if ($CrewCount > 0)

  // nach dem Speichern...

  // Sollen Werte für den nächsten Eintrag übernommen werden?

  if (!$bKeepValues)
  {
    // Eingaben löschen...
    $Boot = "";
    $Datum = "";
    $StartZeit = "";
    $EndeZeit = "";
    $Strecke = "";
    $km = "";
    $Bemerkung = "";
    $Fahrtart = "";
    $bKeepValues = false;
  }

?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="language" content="de">
<meta name="viewport" content="initial-scale=1">
<link rel=stylesheet type="text/css" media="screen" href="fb_screen.css">
<link rel=stylesheet type="text/css" media="handheld" href="fb_mobil.css">
<title>Fahrtenbuch efa mobil - <?php print $Verein; ?></title>
<script type="text/javascript">
<!--
var errfound = false;

function ValidLength(item, len)
{
   return (item.length >= len);
}

function ValidEmail(item)
{
   if (!ValidLength(item, 5)) return false;
   if (item.indexOf ('@', 0) == -1) return false;
   return true;
}

function error(elem, text)
{
   if (errfound) return;
   window.alert(text);
   elem.focus();
   errfound = true;
}

function Validate()
{
   errfound = false;
   if (!ValidLength(document.frmAnmeldung.dfNachname.value,1))
      error(document.frmAnmeldung.dfNachname,"Bitte geben Sie einen Nachnamen ein!");
   if (!ValidLength(document.frmAnmeldung.dfVorname.value,1))
      error(document.frmAnmeldung.dfVorname,"Bitte geben Sie einen Vornamen ein!");
   if (!ValidEmail(document.frmAnmeldung.dfEMail.value))
      error(document.frmAnmeldung.dfEMail, "Bitte eine gültige eMail-Adresse eingeben!");
   return !errfound;
}

function ShowHelpText(obj, Art)
{  
  // for (var i = 0; i < document.getElementsByTagName("input").length; i++)
  //  { document.getElementsByTagName("input")[i].style.backgroundColor = "#ffffff"; }
	
  // obj.style.backgroundColor = "#ffe16b";
  
  switch (Art)
  {
    case "Name":
      Text = "Bitte Namen eingeben: <Nachname>, <Vorname> [ Beispiel Mustermann, Max ]";
      break;
    case "Bootstyp":
      Text = "Bitte Bootstyp auswählen...";
      break;
    case "Datum":
      Text = "Bitte Datum eingeben: TT.MM.JJJJ [ Beispiel 04.06.2012 ]";
      break;
    case "Zeit":
      Text = "Bitte Zeit eingeben: HH:Mi [ Beispiel 10:30]";
      break;
    case "Boot":
      Text = "Bitte Bootsnamen eingeben... [ Beispiel Aubergine (Verein) ]";
      break;
    case "Trommler":
      Text = "Bitte Platz angeben, auf dem der Trommler eingetragen wurde...";
      break;
    case "Bemerkung":
      Text = "Wenn gewünscht, kann eine Bemerkung eingetragen werden...";
      break;
    case "km":
      Text = "Bitte die Kilometer eintragen...";
      break;
    case "Strecke":
      Text = "Bitte die Strecke eintragen... [ Beispiel Schwentine: Eutin - Bootshaus ETSV ]";
      break;
    case "Fahrtart":
      Text = "Bitte die Fahrtart auswählen...";
      break;
    default :
      Text = " ";
      break;
  }
  // Hilfetext anzeigen...
  document.getElementById('hilfe').firstChild.nodeValue = Text;
}

function SetVisibility()
{
  switch (document.frmFahrtenbuch.cmbBootTyp.value)
  {
    case "K1": // Einerkajak
      // Namensfeld für Steuermann ausblenden
      document.getElementById('p0').style.display = "none";
      // Auswahlliste zur Kennzeichnung des Platzes für den Trommler ausblenden
      document.getElementById('p25').style.display = "none";
      // Zahl der notwendigen Namensfelder
      NameFields = 1;
      break;
    case "K2": // Zweierkajak
      // Namensfeld für Steuermann ausblenden
      document.getElementById('p0').style.display = "none";
      // Auswahlliste zur Kennzeichnung des Platzes für den Trommler ausblenden
      document.getElementById('p25').style.display = "none";
      // Zahl der notwendigen Namensfelder
      NameFields = 2;
      break;
    case "C4": // 4er-Kanadier
      // Namensfeld für Steuermann ausblenden
      document.getElementById('p0').style.display = "none";
      // Auswahlliste zur Kennzeichnung des Platzes für den Trommler ausblenden
      document.getElementById('p25').style.display = "none";
      // Zahl der notwendigen Namensfelder
      NameFields = 4;
      break;
    case "C10": // 10er-Mannschaftskanadier
      // Namensfeld für Steuermann anzeigen
      document.getElementById('p0').style.display = "table-row";
      // Auswahlliste zur Kennzeichnung des Platzes für den Trommler ausblenden
      document.getElementById('p25').style.display = "none";
      // Zahl der notwendigen Namensfelder
      NameFields = 9;
      break;
    case "D20": // Drachenboot
      // Namensfeld für Steuermann anzeigen
      document.getElementById('p0').style.display = "table-row";
      // Auswahlliste zur Kennzeichnung des Platzes für den Trommler anzeigen
      document.getElementById('p25').style.display = "table-row";
      // Zahl der notwendigen Namensfelder
      NameFields = 21;
      break;
  }

  if (NameFields == 1)
    document.getElementById('person').firstChild.nodeValue = "PERSON";
  else
    document.getElementById('person').firstChild.nodeValue = "PERSONEN";

  i = 1;

  // notwendige Namensfelder anzeigen
  while (i <= NameFields)
  { 
    id = 'p'+i;
    document.getElementById(String(id)).style.display = "table-row";
    i++;
  } 
  
  i = NameFields+1;

  // nicht benötigte Namensfelder ausblenden
  while (i <= 24)
  {
    id = 'p'+i;
    document.getElementById(String(id)).style.display = "none";
    i++;
  } 

}
// -->
</script>
</head>
<body>
<div align="center">
<img src="/images/logo/iww-logo_75x46.png" width="75" height="46" border="0" alt="Logo Itzehoer Wasser-Wanderer e.V." vspace="5"><br>
<div style="border-bottom-style: solid; border-bottom-width: 2px; border-bottom-color: #ff0000; min-width:300px; max-width: 600px;"></div>
<form name="frmFahrtenbuch" action="/fb/" method="post" onSubmit="return Validate();">
  <?php print "<input name=\"date\" type=\"hidden\" value=\"".time()."\">\r\n"; ?>
  <p class="notseen"><label for="mail">Ihre eMail hier auf keinen Fall eintragen:</label><input id="mail" name="mail" size="60" value=""></p>
  <div style="min-width:300px; max-width: 600px; text-align: left; margin-bottom: 5px; padding: 2px;">
  <font class="pageheader">FAHRTENBUCH</font><br>
  Diese Tour soll in das elektronische Fahrtenbuch efa eingetragen werden...<br>
  </div>
  <div class="formgroup">
  <table width="95%" border="0" cellpadding="<?php print $cell_padding; ?>" cellspacing="0">
  <tr><td colspan="2" id="boot" class="frmHeader">BOOT</td></tr>
  <tr><td width="70">Boot</td><td><input type="text" name="dfBoot" style="width: 100%;" maxlength="40" tabindex="1" OnFocus="ShowHelpText(this,'Boot')"></td></tr>
  <tr><td>Bootstyp</td><td><select name="cmbBootTyp" tabindex="2" OnChange="SetVisibility()" OnFocus="ShowHelpText(this,'Bootstyp');">
      <option value="K1" selected>K1 - Einerkajak</option>
      <option value="K2">K2 - Zweierkajak</option>
      <option value="C4">C4 - Viererkanadier</option>
      <option value="C10">C10 - Mannschaftkanadier</option>
      <option value="D20">D20 - Drachenboot</option>
    </select>
  </td></tr>
  </table>
  </div>
  <div class="formgroup">
  <table width="95%" border="0" cellpadding="<?php print $cell_padding; ?>" cellspacing="0">
  <tr><td colspan="2" id="person" class="frmHeader">PERSON</td></tr>
  <tr id="p0" style="display:none"><td>Steuermann</td><td><input type="text" name="dfSteuermann" style="width: 100%;" maxlength="40" tabindex="3" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p1"><td width="70">Name 1</td><td><input type="text" name="dfName1" style="width: 100%;" maxlength="40" tabindex="4" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p2" style="display:none"><td>Name 2</td><td><input type="text" name="dfName2" style="width: 100%;" maxlength="40" tabindex="5" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p3" style="display:none"><td>Name 3</td><td><input type="text" name="dfName3" style="width: 100%;" maxlength="40" tabindex="6" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p4" style="display:none"><td>Name 4</td><td><input type="text" name="dfName4" style="width: 100%;" maxlength="40" tabindex="7" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p5" style="display:none"><td>Name 5</td><td><input type="text" name="dfName5" style="width: 100%;" maxlength="40" tabindex="8" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p6" style="display:none"><td>Name 6</td><td><input type="text" name="dfName6" style="width: 100%;" maxlength="40" tabindex="9" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p7" style="display:none"><td>Name 7</td><td><input type="text" name="dfName7" style="width: 100%;" maxlength="40" tabindex="10" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p8" style="display:none"><td>Name 8</td><td><input type="text" name="dfName8" style="width: 100%;" maxlength="40" tabindex="11" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p9" style="display:none"><td>Name 9</td><td><input type="text" name="dfName9" style="width: 100%;" maxlength="40" tabindex="12" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p10" style="display:none"><td>Name 10</td><td><input type="text" name="dfName10" style="width: 100%;" maxlength="40" tabindex="13" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p11" style="display:none"><td>Name 11</td><td><input type="text" name="dfName11" style="width: 100%;" maxlength="40" tabindex="14" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p12" style="display:none"><td>Name 12</td><td><input type="text" name="dfName12" style="width: 100%;" maxlength="40" tabindex="15" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p13" style="display:none"><td>Name 13</td><td><input type="text" name="dfName13" style="width: 100%;" maxlength="40" tabindex="16" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p14" style="display:none"><td>Name 14</td><td><input type="text" name="dfName14" style="width: 100%;" maxlength="40" tabindex="17" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p15" style="display:none"><td>Name 15</td><td><input type="text" name="dfName15" style="width: 100%;" maxlength="40" tabindex="18" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p16" style="display:none"><td>Name 16</td><td><input type="text" name="dfName16" style="width: 100%;" maxlength="40" tabindex="19" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p17" style="display:none"><td>Name 17</td><td><input type="text" name="dfName17" style="width: 100%;" maxlength="40" tabindex="20" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p18" style="display:none"><td>Name 18</td><td><input type="text" name="dfName18" style="width: 100%;" maxlength="40" tabindex="21" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p19" style="display:none"><td>Name 19</td><td><input type="text" name="dfName19" style="width: 100%;" maxlength="40" tabindex="22" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p20" style="display:none"><td>Name 20</td><td><input type="text" name="dfName20" style="width: 100%;" maxlength="40" tabindex="23" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p21" style="display:none"><td>Name 21</td><td><input type="text" name="dfName21" style="width: 100%;" maxlength="40" tabindex="24" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p22" style="display:none"><td>Name 22</td><td><input type="text" name="dfName22" style="width: 100%;" maxlength="40" tabindex="25" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p23" style="display:none"><td>Name 23</td><td><input type="text" name="dfName23" style="width: 100%;" maxlength="40" tabindex="26" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p24" style="display:none"><td>Name 24</td><td><input type="text" name="dfName24" style="width: 100%;" maxlength="40" tabindex="27" OnFocus="ShowHelpText(this,'Name')"></td></tr>
  <tr id="p25" style="display:none"><td>Trommler</td><td>auf Platz&nbsp;&nbsp;<select name="cmbTrommler" tabindex="28" OnFocus="ShowHelpText(this,'Trommler')">
      <option value="" selected>...</option>
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
      <option value="5">5</option>
      <option value="6">6</option>
      <option value="7">7</option>
      <option value="8">8</option>
      <option value="9">9</option>
      <option value="10">10</option>
      <option value="11">11</option>
      <option value="12">12</option>
      <option value="13">13</option>
      <option value="14">14</option>
      <option value="15">15</option>
      <option value="16">16</option>
      <option value="17">17</option>
      <option value="18">18</option>
      <option value="19">19</option>
      <option value="20">20</option>
      <option value="21">21</option>
    </select>
  </td></tr>
  </table>
  </div>
  <div class="formgroup">
  <table width="95%" border="0" cellpadding="<?php print $cell_padding; ?>" cellspacing="0">
  <tr><td colspan="2" id="tour" class="frmHeader">TOUR</td></tr>
  <tr><td  width="70" nowrap>Datum</td><td><input type="date" name="dfDatum" size="10" tabindex="29" value="<?php print $Datum; ?>" OnFocus="ShowHelpText(this,'Datum')" style="text-align: center;"></td></tr>
  <tr><td nowrap>von</td><td><input type="time" name="dfStartZeit" size="5" tabindex="30" value="<?php print $StartZeit; ?>" OnFocus="ShowHelpText(this,'Zeit')" style="text-align: center;">&nbsp;Uhr</td></tr>
  <tr><td nowrap>bis</td><td><input type="time" name="dfEndeZeit" size="5" tabindex="31" value="<?php print $EndeZeit; ?>" OnFocus="ShowHelpText(this,'Zeit')" style="text-align: center;">&nbsp;Uhr</td></tr>
  <tr><td>Strecke</td><td><input type="text" name="dfStrecke" style="width: 100%;" maxlength="200" tabindex="32" value="<?php print $Strecke; ?>" OnFocus="ShowHelpText(this,'Strecke')"></td></tr>
  <tr><td>Kilometer</td><td><input type="number" name="dfkm" size="6" maxlength="6" tabindex="33" value="<?php print $km; ?>" OnFocus="ShowHelpText(this,'km')" style="text-align: center;">&nbsp;km</td></tr>
    <tr><td>Fahrtart</td><td><select name="cmbFahrtart" tabindex="34" OnFocus="ShowHelpText(this,'Fahrtart')">
      <option value="Wanderfahrt"<?php if (($Fahrtart == "Wanderfahrt") or ($Fahrtart == "")) print " selected"; ?>>Wanderfahrt</option>
      <option value="Vereinsfahrt"<?php if ($Fahrtart == "Vereinsfahrt") print " selected"; ?>>Vereinsfahrt</option>
      <option value="Verbandsfahrt"<?php if ($Fahrtart == "Verbandsfahrt") print " selected"; ?>>Verbandsfahrt</option>
      <option value="Training"<?php if ($Fahrtart == "Training") print " selected"; ?>>Training</option>
      <option value="Drachenboot-Training"<?php if ($Fahrtart == "Drachenboot-Training") print " selected"; ?>>Drachenboot-Training</option>
      <option value="Drachenboot-Rennen"<?php if ($Fahrtart == "Drachenboot-Rennen") print " selected"; ?>>Drachenboot-Rennen</option>
    </select>
  </td></tr>
  <tr><td>Bemerkung</td><td><input type="text" name="dfBemerkung" style="width: 100%;" maxlength="120" tabindex="35" value="<?php print $Bemerkung; ?>" OnFocus="ShowHelpText(this,'Bemerkung')"></td></tr>
  </table>
  </div>
  <div class="formgroup">
  <table width="95%" border="0" cellpadding="<?php print $cell_padding; ?>" cellspacing="0">
  <tr><td height="35" width="95%"><p id="hilfe" style="color: #0000ff;"> </p></td></tr>
  <tr><td height="20"><input type="checkbox" name="cbKeepValues" tabindex="36" value="1" <?php if ($bKeepValues) print "checked"; ?> OnFocus="ShowHelpText(this,'Die Tourdaten können für die nächste Eingabe wieder verwendet werden...')">&nbsp;Tourdaten für den n&auml;chsten Eintrag merken</td></tr>
  <tr><td align="center"><input type="submit" value="Eintrag absenden" tabindex="37" class="button">&nbsp;<input type="reset" name="pbLoeschen" value="Formular l&ouml;schen" tabindex="38" class="button" OnFocus="ShowHelpText(this,'')"></td></tr>
  </table>
</div>
</form>
<div style="min-width:300px; max-width: 600px; text-align: left; margin-bottom: 5px; padding: 2px;">
Die hier erfassten Fahrtenbucheintr&auml;ge werden gesammelt und in einer Importdatei zusammengefasst. Die &Uuml;bernahme in das elektronische Fahrtenbuch efa im Bootshaus erfolgt nicht direkt, sondern wird in Abst&auml;nden vorgenommen.<br>
</div>
Version <?php print $Version; ?><br>
<br>
<a href="http://www.itzehoer-wasser-wanderer.de/" target="_top"><img src="/images/logo/iww-logo_40x25.jpg" width="40" height="25" border="0" vspace="6" alt="Logo Itzehoer Wasser-Wanderer e.V."></a><br>
<font class="small">&copy; copyright 2012</font><br>
Itzehoer Wasser-Wanderer e.V.<br>
<a href="http://www.itzehoer-wasser-wanderer.de/" target="_top" class="small">www.itzehoer-wasser-wanderer.de</a><br>
<br>
<font class="small">programmiert von</font><br>
Martin &Ouml;lscher<br>
<br>
</div>
</body>
</html>
