package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.logic.Abilities.*
import com.demo.app.ncov2020.logic.Disease.*
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater

object GameProperties {
     val abilityMap = HashMap<String, Ability>()
     val transmissionMap = HashMap<String, Transmission>()
     val symptomMap = HashMap<String, Symptom>()

    init {
        symptomMap["pnevmonia"] = Symptom("Pnevmonia", "Hard to breathe", 2.0, 7, 0.01,26)
        symptomMap["cough"] = Symptom("Cough", "A-a-a-pchi. Chance of infection by spreading pathogen into surroudings, especially in high density, urban areas.", 2.0, 4, 0.0,10)
        symptomMap["headache"] = Symptom("Headache", "O-h my head", 0.1, 1, 0.0,1)
        symptomMap["snot"] = Symptom("Snot", "Messy", 0.1, 2, 0.0,1)
        symptomMap["nausea"] = Symptom("Nausea", "Irritates stomach leads to discomfort", 0.2, 2, 0.0,3)
        symptomMap["sweating"] = Symptom("Sweating", "The loss fluid through sweating also increases infection rates due to poor hygiene.", 0.2, 2, 0.0,3)
        symptomMap["skinLesions"] = Symptom("SkinLesions", "Breakdown in the epidermus causes large open wound which significantly increse infectivity.", 0.7, 4, 0.02,13)
        abilityMap["antibiotics"] = Ability("Antibiotics1", "Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1, HandlerAntibiotics1(),6)
        abilityMap["antibiotics"] = Ability("Antibiotics1", "Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS2, HandlerAntibiotics2(),8)
        abilityMap["antibiotics"] = Ability("Antibiotics1", "Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS3, HandlerAntibiotics3(),10)
        abilityMap["antibiotics"] = Ability("HotResistance", "Better infectivity in hot climate", TypeAbility.HOT, HandlerHot(),5)
        abilityMap["antibiotics"] = Ability("ColdResistance", "Better infectivity in cold climate", TypeAbility.COLD, HandlerCold(),5)
        transmissionMap["plains"] = Transmission("Plains transmission", "You will be able to infect by plains", TypeTrans.AIR, HandlerAIR(),4)
        transmissionMap["tourist"] = Transmission("Tourist transmission", "You will be able to infect by tourists", TypeTrans.GROUND, HandlerGround(),4)
        transmissionMap["ship"] = Transmission("Ship transmission", "You will be able to infect by ships", TypeTrans.WATER, HandlerWater(),4)

    }
}