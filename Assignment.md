## Zadání

### Model pro objednávky v restauraci

Připrav třídy pro realizaci datového modelu sledování objednávek v restauraci. Vytvořený kód bude součástí backendu aplikace pro terminály, které ovládají číšníci a kuchaři.

Celý systém má kromě evidence čekajících a vyřízených objednávek poskytovat i informace pro účetnictví a podklady pro hledání optimalizací a slabých míst v přípravě a vydávání jídel.

Tvým úkolem je vytvořit datový model a zpracovat základní statistické metody.

### Co sledujeme?

#### Zásobník receptů

Kuchaři mají připraven zásobník jídel (dish + cook book). U každého jídla/receptu ukládáme název (title), cenu (price), přibližnou dobu přípravy (preparation time) v minutách a URL odkaz na fotografii (image). Fotografie samotná je na serveru, její ukládání tedy neřešíš. Ulož pouze název souboru bez přípony — například ```bolonske-spagety-01```. (Všechny obrázky jsou ve formátu .PNG, ani příponu tedy nemusíme ukládat.)

Doba přípravy musí být kladné číslo — systém nepovolí zadat záporné číslo či nulu.

Kuchaři mají možnost některá jídla ze zásobníku vyřadit, přidat, nebo upravit. V systému kvůli budoucímu zobrazování nesmí být jídlo/recept bez fotografie, ale na serveru je speciální fotografie s názvem ```blank```, kterou použij jako výchozí pro recepty, které zatím fotografii nemají.

#### Objednávky (orders)

Restaurace má očíslované stoly. Zákazníci u těchto stolů si objednávají jídla. Jedna objednávka popisuje objednání jednoho konkrétního jídla a vztahuje se ke konkrétnímu stolu. U každé objednávky sleduj jaké jídlo bylo objednáno, kolik kusů tohoto jídla bylo objednáno, kdy byla objednávka zadána a kdy vyřízena (orderedTime a fulfilmentTime) a zda je zaplacena.

Například:

- Zákazníci u stolu 8 si objednali 4 ks minerální vody jemně perlivé dnes v 15:38:17. Zatím vodu nedostali a objednávka není zaplacena.

### Ukládání dat

Údaje ukládej do textového souboru či textových souborů. (Není nutné, aby vše bylo v jednom souboru.) Celá evidence by měla jít uložit a následně obnovit do stejného stavu: zásobník receptů, objednávky a jejich stav.

Připrav metody pro načtení a pro uložení celé evidence z/do souboru/souborů. Pokud soubory dosud neexistují, aplikace by měla nastartovat a běžet, jen zatím nebudou zadaná k dispozici žádná jídla, objednávky atd.

### Ošetření chybových stavů

V případě, že jsou soubory poškozeny, aplikace se musí zachovat korektně (nesmí havarovat) a musí vhodně zareportovat chybu tak, aby bylo možné opravit poškozená místa. Stačí reportovat jedno poškozené místo — v případě jakékoli chyby aplikace nemusí pokračovat v načítání souborů.

Chybové stavy deleguj do třídy ```Main```, kde vypíšeš chybové hlášení na chybový výstup.

### Požadované výstupy

Management restaurace chce mít kdykoli k dispozici informace a možnosti vyjmenované dále. Připrav třídu ```RestaurantManager``` s metodami pro získání těchto informací.

Požadované informace:

1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
2. Možnost seřadit objednávky podle času zadání.
3. Průměrnou dobu zpracování objednávek.
   - Pro uchování času potřebuješ třídu LocalDateTime, která ukládá i čas.
   - Počítej dobu zpracování objednávky v minutách, nemusíš řešit sekundy.
   - Počet minut mezi dvěma údaji lze zjistit pomocí knihovních funkcí Javy — zkus hledat. Nemusíš si to programovat sám a ani by to nebylo rozumné. Když už je to hotové, neztrácej tím čas!

4. Seznam jídel, která byla dnes objednána. Bez ohledu na to, kolikrát bylo dané jídlo objednáno.

5. Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku):

```plaintext
** Objednávky pro stůl č.  4 **
****
1. Kofola velká 4x (130 Kč):    15:25-15:29 zaplaceno
2. Pizza Grande (130 Kč):   15:29-16:10 zaplaceno
3. Nanuk Míša (30 Kč):  17:12-17:18
******
```

Obecně jsou na řádku položky:

```plaintext
1. [číslo položky].[mezera]
2. [název jídla][mezera]
3. [počet kusů v objednávce][mezera][(][celková cena včetně textu " Kč"][)]
4. [dvojtečka][tabulátor]
5. [čas zadání objednávky ve formátu hh:mm]
6. [-]
7. [čas vyřízení ve formátu hh:mm][tabulátor]
8. [text "zaplaceno", pokud již byla zaplacena – jinak nic]
```

Číslo stolu na začátku je dvojmístné, pro stoly 1-9 se před číslo umístí mezera.

Zadaný formát je třeba beze zbytku dodržet, protože jej zpracovává navazující automatizovaný systém. Zejména si dej pozor na použití tabulátorů místo některých mezer.

## Testovací scénář

### Připrav kód pro ověření

Připrav testovací scénář a zapiš do třídy ```Main``` kód tohoto scénáře.

Pro každý úkol připrav jednu metodu. Pokud lze daný úkol vyřešit na max. třech řádcích, nemusíš vytvářet metodu a můžeš kód přímo zapsat do metody ```main```.

V metodě main pak zavolej jednotlivé metody a u každé uveď v komentáři číslo úkolu, který řeší. Úkoly spouštěj v pořadí, jak jsou uvedeny:

1. Načti stav evidence z disku. Pokud se aplikace spouští poprvé a soubory neexistují, budou veškeré seznamy a repertoár zatím prázdné. (Aplikace nepotřebuje předvytvořené žádné soubory.)
2. Připrav testovací data. Vlož do systému:
    - jídla:
        - Kuřecí řízek obalovaný 150 g
        - Hranolky 150 g
        - Pstruh na víně 200 g
        - Kofola 0,5 l
    - objednávky:
        - Zákazníci u stolu 15 si objednali dvakrát kuřecí řízek, dvakrát hranolky a dvakrát Kofolu. Kofolu už dostali, na řízek ještě čekají.
        - Vytvoř také objednávku pro stůj číslo 2.
3. Vypiš celkovou cenu konzumace pro stůl číslo 15.
4. Použij všechny připravené metody pro získání informací pro management — údaje vypisuj na obrazovku.
5. Pokud potřebuješ, přidej si do systému další informace (jídla, objednávky atd.).
6. Změněná data ulož na disk.
7. Po opětovném spuštění aplikace musí být data opět v pořádku načtena. (Vyzkoušej!)

### Načtení neplatného souboru

Pokud budou data ve vstupních souborech poškozená či v nesprávném formátu, aplikace se při spuštění s těmito soubory musí zachovat korektně — zahlásí chybu a bude pokračovat bez načtených dat. Aplikace nesmí havarovat.


## Poznámky
### Odevzdej včas to, co požaduje zadání

Snaž se řešení projektu odevzdat v termínu. Až odevzdáš základní řešení, můžeš dále experimentovat. Nekomplikuj ale řešení zbytečnými experimenty navíc, pokud ještě nemáš odevzdánu základní verzi.

### Vyčisti kód před odevzdáním!

Před odevzdáním projektu proveď vyčištění kódu do podoby produkčního kódu!

Zejména:

1. Odstraň veškerý kód, který se nepoužívá.
    Nevyužívaný kód poznáš v IntelliJ IDEA tak, že je šedivý. Týká se také tříd, které nakonec nepotřebuješ.
    Výjimkou jsou případné nepoužité přístupové metody (gettery a settery) a metody a atributy, které jsou výslovně vyžadované v zadání.
    Pokud si nevyužitý kód chceš ponechat, ulož si ho jinam mimo projekt. Odevzdej pouze kód, který je součástí řešení.

2.    Aplikace musí provádět právě jen požadované akce a nic jiného.
    Pokud sis přidal další testovací a jiný kód, odstraň jej z projektu.

3.    Metody delší než 20 řádků rozlož na více metod. Týká se i metody main.

4.    Odstraň zakomentovaný kód.
    Zůstat smí jen komentáře, které jsou potřeba k vysvětlení funkce aplikace. Bylo by ale lepší napsat kód tak, aby byl čitelný sám o sobě a komentáře nepotřeboval.
    Veškeré komentáře ale musí být aktualizované a odpovídat aktuálnímu fungování projektu. Zastaralé nebo nepotřebné komentáře vymaž!

5.    Zkontroluj logické pojmenování tříd, atributů, metod a balíčků.
    Případně podle potřeby názvy uprav pomocí refaktoringu (pravé tlačítko → Refactor... → Rename...).

Kód, který nesplňuje výše uvedené požadavky bude považován za nesprávný.

### Chybějící údaje:

Pokud ti některé informace v zadání chybí, můžeš si je zvolit sám/sama. V praxi by ses samozřejmě ptal(a) zadavatele. Zde nemáš možnost komunikace se zadavatelem a je na tobě jako vývojáři/vývojářce, abys některá rozhodnutí učinil(a) sám/sama.

Pokus se svá rozhodnutí činit tak, aby byl výsledný systém:

1. jednoduchý a elegantní a abys projekt mohl(a) brzo odevzdat,
2. přehledný a dobře udržovatelný,
3. do budoucna dobře rozšiřovatelný,
4. a přitom samozřejmě splňoval veškerou požadovanou funkcionalitu.

Při psaní samozřejmě dodržuj doporučené konvence a best-practices, které jsme se naučili.

Máš ale samozřejmě možnost ptát se svého lektora — zejména v případě, že něco nesedí, nebo si myslíš, že jsme na něco zapomněli. Jak se říká: „Když přemýšlíš, jestli tvoje otázka stojí za dotaz, nepřemýšlej a zeptej se!“

### Nezapomeň zkontrolovat!

1. Správný formát výpisu objednávek včetně použití tabulátorů.
2. Aplikace musí korektně nastartovat, když soubory neexistují.
3. Aplikace musí umět znovu načíst soubory, které vygenerovala.
