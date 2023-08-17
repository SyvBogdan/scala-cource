package net.study.functional.lesson10_OOP_classes

sealed trait ResponseStatus

case object ActiveStatus extends ResponseStatus

case object StoppedStatus extends ResponseStatus

case object PendingStatus extends ResponseStatus

