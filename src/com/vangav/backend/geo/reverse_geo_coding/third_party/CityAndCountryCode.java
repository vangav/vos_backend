
package com.vangav.backend.geo.reverse_geo_coding.third_party;

/*
The MIT License (MIT)
[OSI Approved License]
The MIT License (MIT)

Copyright (c) 2014 Daniel Glasson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Comparator;

import com.vangav.backend.data_structures_and_algorithms.third_party.kd_tree.KDNodeComparator;

/**
 * Created by Daniel Glasson on 18/05/2014. This class works with a placenames
 * files from http://download.geonames.org/export/dump/
 */

public class CityAndCountryCode extends KDNodeComparator<CityAndCountryCode> {
  
  private String  name;
  @SuppressWarnings("unused")
  private boolean majorPlace;             // Major or minor place
  private double  latitude;
  private double  longitude;
  private double  point[] = new double[3]; // The 3D coordinates of the point
  private String  country;

  public CityAndCountryCode(String data) {
    String[] names = data.split("\t");
    name = names[1];
    majorPlace = names[6].equals("P");
    latitude = Double.parseDouble(names[4]);
    longitude = Double.parseDouble(names[5]);
    setPoint();
    country = names[8];
  }

  public CityAndCountryCode(Double latitude, Double longitude) {
    name = country = "Search";
    this.latitude = latitude;
    this.longitude = longitude;
    setPoint();
  }
  
  public String getCity () {
    
    return this.name;
  }
  
  public String getCountryCode () {
    
    return this.country;
  }

  private void setPoint() {
    point[0] = cos(toRadians(latitude)) * cos(toRadians(longitude));
    point[1] = cos(toRadians(latitude)) * sin(toRadians(longitude));
    point[2] = sin(toRadians(latitude));
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  protected double squaredDistance(CityAndCountryCode other) {
    double x = this.point[0] - other.point[0];
    double y = this.point[1] - other.point[1];
    double z = this.point[2] - other.point[2];
    return (x * x) + (y * y) + (z * z);
  }

  @Override
  protected double axisSquaredDistance(CityAndCountryCode other, int axis) {
    double distance = point[axis] - other.point[axis];
    return distance * distance;
  }

  @Override
  protected Comparator<CityAndCountryCode> getComparator(int axis) {
    return CityAndCountryCodeComparator.values()[axis];
  }

  protected static enum CityAndCountryCodeComparator implements Comparator<CityAndCountryCode> {
    x {
      @Override
      public int compare(CityAndCountryCode a, CityAndCountryCode b) {
        return Double.compare(a.point[0], b.point[0]);
      }
    },
    y {
      @Override
      public int compare(CityAndCountryCode a, CityAndCountryCode b) {
        return Double.compare(a.point[1], b.point[1]);
      }
    },
    z {
      @Override
      public int compare(CityAndCountryCode a, CityAndCountryCode b) {
        return Double.compare(a.point[2], b.point[2]);
      }
    };
  }
}
