package no.nav.paw.arbeidssokerregisteret.application.fakta

import no.nav.paw.arbeidssokerregisteret.application.Fakta
import no.nav.paw.pdl.graphql.generated.hentperson.Bostedsadresse

fun adresseFakta(adresse: Bostedsadresse?): Fakta {
    return when {
        adresse == null -> Fakta.INGEN_ADRESSE_FUNNET
        adresse.vegadresse?.kommunenummer != null -> Fakta.HAR_NORSK_ADRESSE
        adresse.matrikkeladresse?.kommunenummer != null -> Fakta.HAR_NORSK_ADRESSE
        adresse.ukjentBosted?.bostedskommune != null -> Fakta.HAR_NORSK_ADRESSE
        adresse.utenlandskAdresse != null -> Fakta.HAR_UTENLANDSK_ADRESSE
        else -> Fakta.INGEN_ADRESSE_FUNNET
    }
}