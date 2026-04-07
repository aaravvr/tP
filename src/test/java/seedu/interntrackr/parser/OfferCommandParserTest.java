//@@author N-SANJAI
package seedu.interntrackr.parser;

import org.junit.jupiter.api.Test;
import seedu.interntrackr.exception.InternTrackrException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class OfferCommandParserTest {

    @Test
    public void parse_validInput_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/5000");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("2 s/6500.75");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("10 s/0");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("99 s/1000.00");
        });
    }

    @Test
    public void parse_validInputWithWhitespace_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/ 5000"); // Space after prefix
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("  2  s/6500.75"); // Extra leading spaces
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("3 s/ 9999.99 "); // Space after salary
        });
    }

    @Test
    public void parse_boundaryValidSalaries_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/10000000"); // Exactly at limit (10 million)
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/9999999.99"); // Just below limit
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/0.01"); // Minimum non-zero
        });
    }

    @Test
    public void parse_emptyInput_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("    ");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("\t\n");
        });
    }

    @Test
    public void parse_missingSalaryPrefix_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("5 d/5000"); // Wrong prefix
        });
    }

    @Test
    public void parse_invalidSalaryFormat_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/five-thousand");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/abc");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/$5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/5,000");
        });
    }

    @Test
    public void parse_negativeSalary_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/-500");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/-0.01");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("5 s/-10000000");
        });
    }

    @Test
    public void parse_tooManyDecimalPlaces_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/5000.123");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/5000.5555");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/1.999");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/100.9999999");
        });
    }

    @Test
    public void parse_exactlyTwoDecimalPlaces_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/5000.00");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/0.50");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/9999.99");
        });
    }

    @Test
    public void parse_oneDecimalPlace_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/5000.5");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/0.1");
        });
    }

    @Test
    public void parse_missingIndex_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse(" s/5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("s/5000");
        });
    }

    @Test
    public void parse_invalidIndexFormat_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("one s/5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1.5 s/5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1a s/5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("abc s/5000");
        });
    }

    @Test
    public void parse_zeroIndex_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("0 s/5000");
        });
    }

    @Test
    public void parse_negativeIndex_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("-1 s/5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("-999 s/5000");
        });
    }

    @Test
    public void parse_largeIndex_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("999999 s/5000");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("2147483647 s/5000"); // Max int value
        });
    }

    @Test
    public void parse_excessivelySalary_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/10000001"); // Over limit
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/99999999");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/8923479283479238498347892374982374"); // Extremely large
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/100000000.00");
        });
    }

    @Test
    public void parse_emptySalaryValue_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/   ");
        });
    }

    @Test
    public void parse_multipleSPrefixes_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/5000 s/6000");
        });
    }

    @Test
    public void parse_specialCharactersInSalary_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/5000@");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/50#00");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/50!00");
        });
    }

    @Test
    public void parse_scientificNotationInput_throwsException() {
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/1e6"); // Scientific notation
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 s/5E3");
        });
    }

    @Test
    public void parse_trailingWhitespace_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/5000   ");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("2 s/6500.50  ");
        });
    }

    @Test
    public void parse_caseInsensitivityForSPrefix_throwsException() {
        // Only lowercase 's/' is valid
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 S/5000");
        });
        assertThrows(InternTrackrException.class, () -> {
            OfferCommandParser.parse("1 S/6500");
        });
    }

    @Test
    public void parse_salaryZero_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/0");
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/0.00");
        });
    }

    @Test
    public void parse_realWorldExamples_returnsOfferCommand() {
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("1 s/5000.00"); // Typical salary
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("5 s/75000"); // Larger salary
        });
        assertDoesNotThrow(() -> {
            OfferCommandParser.parse("10 s/9999999.99"); // Near max valid
        });
    }
}
