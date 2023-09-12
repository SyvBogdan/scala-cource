package net.study.functional.lesson10_OOP_classes.hometask.oop.request

import java.util.Date

/**
 * if present, all params must be absent
 *
 * @param organisation
 * @param position not empty
 * @param from     must be less or equal to
 * @param to       must be greater or equal from
 */
case class Experience(organisation: Option[String], position: Option[String], from: Option[Date], to: Option[Date])

/**
 * @param login    must be not empty
 * @param password must be right
 * */

////Requests
case class SignInRequest(
                          login: Option[String],
                          password: Option[String]
                        )

/**
 * @param name    must be not empty, latin symbols only
 * @param surname must be not empty, latin symbols only
 * @param login   must be not empty, latin symbols and/or digits only  and unique across service
 * @param msisdn  must be not empty, only digits  symbols and has 9 or 12 symbols
 */
case class SignUpRequest(
                          name: Option[String],
                          surname: Option[String],
                          login: Option[String],
                          pass: Option[String],
                          msisdn: Option[String]
                        )

/**
 * @param workExperience can be optional, but can't be empty, and if present any Experience object, its fields must be present!
 * @param certificates   can pe optional, but can't be empty
 *                       at least work experience or certificates must be present
 * */
case class WorkInfoRequest(
                            login: Option[String],
                            workExperience: Option[List[Experience]],
                            certificates: Option[List[String]]
                          )
