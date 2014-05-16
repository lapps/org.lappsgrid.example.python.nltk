org.lappsgrid.example.python.nltk
=================================

Python Wrapping NLTK Tagger Example


### NLTK Example

    ```bash
    shis-MacBook-Air:resources shi$ python nltk_tagger.py "How are you today?"
    [('How', 'WRB'), ('are', 'VBP'), ('you', 'PRP'), ('today', 'NN'), ('?', '.')]
    shis-MacBook-Air:resources shi$
    ```

### Python-to-Java Mapping

     *<p>   PYTHON    ---->     JAVA		[[[[ RETURN VALUES ]]]
     *<p>   ------              ----
     *<p>   None                null
     *<p>   bool                boolean
     *<p>   int                 int
     *<p>   long                long or BigInteger  (depending on size)
     *<p>   string              String
     *<p>   unicode             String
     *<p>   complex             net.razorvine.pickle.objects.ComplexNumber
     *<p>   datetime.date       java.util.Calendar
     *<p>   datetime.datetime   java.util.Calendar
     *<p>   datetime.time       java.util.Calendar
     *<p>   datetime.timedelta  net.razorvine.pickle.objects.TimeDelta
     *<p>   float               double   (float isn't used)
     *<p>   array.array         array of appropriate primitive type (char, int, short, long, float, double)
     *<p>   list                java.util.List<Object>
     *<p>   tuple               Object[]
     *<p>   set                 java.util.Set
     *<p>   dict                java.util.Map
     *<p>   bytes               byte[]
     *<p>   bytearray           byte[]
     *<p>   decimal             BigDecimal
     *<p>   custom class        Map<String, Object>  (dict with class attributes including its name in "__class__")
