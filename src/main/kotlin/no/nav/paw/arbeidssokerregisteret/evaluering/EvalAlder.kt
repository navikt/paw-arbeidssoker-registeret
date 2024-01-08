package no.nav.paw.arbeidssokerregisteret.evaluering

import no.nav.paw.pdl.graphql.generated.hentperson.Foedsel
import java.time.LocalDate
import java.time.Month

fun evalAlder(foedsel: Foedsel?): Set<Evaluation> {
    val dateOfBirth = foedsel?.foedselsdato?.let(LocalDate::parse)
    val lastDayInYearOfBirth = { foedsel?.foedselsaar?.let { LocalDate.of(it, Month.DECEMBER, 31) } }
    val dob = dateOfBirth ?: lastDayInYearOfBirth()
    val preliminaryEvalResult = if (dateOfBirth != null) emptySet() else setOf(Evaluation.UKJENT_FOEDSELSDATO)
    return if (dob != null) {
        if (isAtLeast18YearsAgo(dob)) {
            preliminaryEvalResult + Evaluation.ER_OVER_18_AAR
        } else {
            preliminaryEvalResult + Evaluation.ER_UNDER_18_AAR
        }
    } else {
        preliminaryEvalResult + Evaluation.UKJENT_FOEDSELSAAR
    }
}

fun isAtLeast18YearsAgo(date: LocalDate) = isAtLeastYearsAgo(18, date)
fun isAtLeastYearsAgo(yearLimitInclusive: Long, date: LocalDate): Boolean =
    LocalDate.now().minusYears(yearLimitInclusive) >= date