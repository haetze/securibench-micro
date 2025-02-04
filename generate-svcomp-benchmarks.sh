#!/bin/bash

mkdir -p securibench/micro
cp -a src/main/java/* securibench/micro/

for f in `find src/main/java/securibench/micro | grep java`; do
  ipath=`echo $f | sed -e "s/\.java$//"`
  iname=`echo $ipath | cut -d '/' -f 7`
  if [ -z "$iname" ]; then
    continue
  fi

  safe=`less $f | grep "vuln_count" | grep "\"0\"" | wc -l` 
  verdict=false
  if [ "1" -eq "$safe" ]; then
    verdict=true
  fi

  echo "## Generating: $iname"
  
  cat <<EOT >> securibench/$iname.yml
format_version: "2.0"
input_files:
  - ../common/
  - micro/
  - $iname/
properties:
  - property_file: ../properties/assert.prp
    expected_verdict: $verdict

options:
  language: Java

EOT

  mkdir securibench/$iname
  # cp -a src/main/java/* $iname

  import=`echo $ipath | sed -e "s/\//./g" | sed -e "s/src.main.java.//"`

  cat <<EOT >> securibench/$iname/Main.java
// SPDX-FileCopyrightText: 2021 Falk Howar falk.howar@tu-dortmund.de
// SPDX-License-Identifier: Apache-2.0
    
// This file is part of the SV-Benchmarks collection of verification tasks:
// https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks

import org.sosy_lab.sv_benchmarks.Verifier;
import $import;
import mockx.servlet.http.HttpServletRequest;
import mockx.servlet.http.HttpServletResponse;
import mockx.servlet.http.Cookie;
import java.io.IOException;

public class Main {
  
  public static void main(String[] args) {
    String s1 = Verifier.nondetString();
    HttpServletRequest req = new HttpServletRequest();
    HttpServletResponse res = new HttpServletResponse();
    req.setTaintedValue(s1);

    $iname sut = new $iname();
    try {
      sut.doGet(req, res);
    } 
    catch (IOException e) {

    }
  }
}

EOT

  # javac -cp ../tests:target/securibench-0.1-SNAPSHOT.jar securibench/$iname/Main.java

done