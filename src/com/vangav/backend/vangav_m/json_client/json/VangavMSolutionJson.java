/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.backend.vangav_m.json_client.json;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.content.checking.JavaCodeVerifierInl;
import com.vangav.backend.content.checking.StringVerifierInl;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.rest_client.RestRequestPostJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * VangavMSolutionJson represents a Vangav M solution
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VangavMSolutionJson extends RestRequestPostJson {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "VangavMSolutionJson";
  }

  @Override
  @JsonIgnore
  protected RestRequestPostJson getThis () throws Exception {
    
    return this;
  }
  
  @JsonProperty
  public String solution_type;
  @JsonProperty
  public String name;
  @JsonProperty
  public InputMultiRangeJson[] inputs_multi_range;
  @JsonProperty
  public InputJson[] inputs;
  @JsonProperty
  public String[] finite_inputs;
  @JsonProperty
  public OutputMultiRangeJson[] outputs_multi_range;
  @JsonProperty
  public OutputJson[] outputs;
  @JsonProperty
  public RelationJson[] relations;
  @JsonProperty
  public RelativeRelationJson[] relative_relations;
  
  /**
   * verify
   * throws an exception if this Vangav M Solution JSON Object is invalid
   * @throws Exception
   */
  @JsonIgnore
  public void verify () throws Exception {
    
    // check mandatory entries (not null and each has at least one
    //   character/entry
    
    ArgumentsInl.checkNotEmpty(
      "vangav solution type",
      this.solution_type,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkNotEmpty(
      "vangav solution name",
      this.name,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkNotEmpty(
      "vangav solution inputs",
      this.inputs,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkNotEmpty(
      "vangav solution outputs",
      this.outputs,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkNotEmpty(
      "vangav solution relations",
      this.relations,
      ExceptionType.CODE_EXCEPTION);
    
    // solution type must be JAVA
    if (this.solution_type.compareToIgnoreCase("JAVA_JAR") != 0) {
      
      throw new CodeException(
        "Invalid vangav solution type ["
        + this.solution_type
        + "], this client handles JAVA solutions only where the solution type "
        + "must be equal to [JAVA_JAR] for other solution type use the "
        + "Vangav M solutions client or the web client available on "
        + "www.vangav.com",
        ExceptionClass.JSON);
    }
    
    // verify solution's name
    JavaCodeVerifierInl.verifyIdentifier(
      "vangav solution name",
      this.name);

    // verify multi-range inputs
    
    Set<String> multiRangeInputNames = new HashSet<String>();

    if (this.inputs_multi_range != null) {
      
      for (InputMultiRangeJson inputMultiRangeJson : this.inputs_multi_range) {
        
        ArgumentsInl.checkNotEmpty(
          "input multi range suffixes",
          inputMultiRangeJson.suffixes,
          ExceptionType.CODE_EXCEPTION);
        
        for (String suffix : inputMultiRangeJson.suffixes) {
          
          JavaCodeVerifierInl.verifyIdentifier(
            "input multi range name",
            inputMultiRangeJson.prefix + suffix);
          
          if (multiRangeInputNames.contains(
                inputMultiRangeJson.prefix + suffix) == true) {
            
            throw new CodeException(
              "Duplicate mutli range input, prefix ["
              + inputMultiRangeJson.prefix
              + "] suffix ["
              + suffix
              + "]",
              ExceptionClass.JSON);
          }
          
          multiRangeInputNames.add(inputMultiRangeJson.prefix + suffix);
        }
      }
    }
    
    // verify inputs
    
    final double kMinInputOutputRange = 0.02;
    final double kMinMidDelta = 0.01;
    
    Set<String> inputNames = new HashSet<String>();

    for (InputJson inputJson : this.inputs) {
      
      JavaCodeVerifierInl.verifyIdentifier(
        "input name",
        inputJson.name);
      
      if (multiRangeInputNames.contains(inputJson.name) == true) {
        
        multiRangeInputNames.remove(inputJson.name);
      }
      
      if (inputNames.contains(inputJson.name) == true) {
        
        throw new CodeException(
          "duplicate input name ["
          + inputJson.name
          + "]",
          ExceptionClass.JSON);
      }
      
      inputNames.add(inputJson.name);
      
      ArgumentsInl.checkDoubleHasValue(
        "Input ["
        + inputJson.name
        + "] min",
        inputJson.min,
        ExceptionType.CODE_EXCEPTION);
      ArgumentsInl.checkDoubleHasValue(
        "Input ["
        + inputJson.name
        + "] max",
        inputJson.max,
        ExceptionType.CODE_EXCEPTION);
      ArgumentsInl.checkDoubleHasValue(
        "Input ["
        + inputJson.name
        + "] mid",
        inputJson.mid,
        ExceptionType.CODE_EXCEPTION);
      
      if (((inputJson.max - inputJson.min) < kMinInputOutputRange) ||
          ((inputJson.max - inputJson.mid) < kMinMidDelta) ||
          ((inputJson.mid - inputJson.min) < kMinMidDelta) ) {
        
        throw new CodeException(
          "input ["
          + inputJson.name
          + "] min, mid, max ["
          + inputJson.min
          + ", "
          + inputJson.mid
          + ", "
          + inputJson.max
          + "] must have a minimum delta between min and max of ["
          + kMinInputOutputRange
          + "] and must have a minimum delta between mid and min/max of ["
          + kMinMidDelta
          + "]",
          ExceptionClass.JSON);
      }
    }
    
    if (multiRangeInputNames.isEmpty() == false) {
      
      throw new CodeException(
        "multi range inputs ["
        + Arrays.toString(multiRangeInputNames.toArray() )
        + "] have no corresponding inputs in ["
        + Arrays.toString(inputNames.toArray() )
        + "]",
        ExceptionClass.JSON);
    }
    
    // verify finite inputs
    if (this.finite_inputs != null) {
      
      for (String finiteInput : this.finite_inputs) {
        
        ArgumentsInl.checkNotEmpty(
          "finite input ",
          finiteInput,
          ExceptionType.CODE_EXCEPTION);
        
        if (inputNames.contains(finiteInput) == false) {
          
          throw new CodeException(
            "finite input ["
            + finiteInput
            + "] doesn't belong to solution's inputs ["
            + Arrays.toString(inputNames.toArray() )
            + "]",
            ExceptionClass.JSON);
        }
      }
    }
    
    // verify multi-range outputs
    
    Set<String> multiRangeOutputNames = new HashSet<String>();

    if (this.outputs_multi_range != null) {
      
      for (OutputMultiRangeJson outputMultiRangeJson :
           this.outputs_multi_range) {
        
        ArgumentsInl.checkNotEmpty(
          "output multi range suffixes",
          outputMultiRangeJson.suffixes,
          ExceptionType.CODE_EXCEPTION);
        
        for (String suffix : outputMultiRangeJson.suffixes) {
          
          JavaCodeVerifierInl.verifyIdentifier(
            "output multi range name",
            outputMultiRangeJson.prefix + suffix);
          
          if (multiRangeOutputNames.contains(
                outputMultiRangeJson.prefix + suffix) == true) {
            
            throw new CodeException(
              "Duplicate mutli range output, prefix ["
              + outputMultiRangeJson.prefix
              + "] suffix ["
              + suffix
              + "]",
              ExceptionClass.JSON);
          }
          
          multiRangeOutputNames.add(outputMultiRangeJson.prefix + suffix);
        }
      }
    }
    
    // verify outputs
    
    final double kMinOutputOutputRange = 0.02;
    
    Set<String> outputNames = new HashSet<String>();

    for (OutputJson outputJson : this.outputs) {
      
      JavaCodeVerifierInl.verifyIdentifier(
        "output name",
        outputJson.name);
      
      if (multiRangeOutputNames.contains(outputJson.name) == true) {
        
        multiRangeOutputNames.remove(outputJson.name);
      }
      
      if (outputNames.contains(outputJson.name) == true) {
        
        throw new CodeException(
          "duplicate output name ["
          + outputJson.name
          + "]",
          ExceptionClass.JSON);
      }
      
      outputNames.add(outputJson.name);
      
      ArgumentsInl.checkDoubleHasValue(
        "Output ["
        + outputJson.name
        + "] min",
        outputJson.min,
        ExceptionType.CODE_EXCEPTION);
      ArgumentsInl.checkDoubleHasValue(
        "Output ["
        + outputJson.name
        + "] max",
        outputJson.max,
        ExceptionType.CODE_EXCEPTION);
      ArgumentsInl.checkDoubleHasValue(
        "Output ["
        + outputJson.name
        + "] mid",
        outputJson.mid,
        ExceptionType.CODE_EXCEPTION);
      
      if (((outputJson.max - outputJson.min) < kMinInputOutputRange) ||
          ((outputJson.max - outputJson.mid) < kMinMidDelta) ||
          ((outputJson.mid - outputJson.min) < kMinMidDelta) ) {
        
        throw new CodeException(
          "output ["
          + outputJson.name
          + "] min, mid, max ["
          + outputJson.min
          + ", "
          + outputJson.mid
          + ", "
          + outputJson.max
          + "] must have a minimum delta between min and max of ["
          + kMinOutputOutputRange
          + "] and must have a minimum delta between mid and min/max of ["
          + kMinMidDelta
          + "]",
          ExceptionClass.JSON);
      }
    }
    
    if (multiRangeOutputNames.isEmpty() == false) {
      
      throw new CodeException(
        "multi range outputs ["
        + Arrays.toString(multiRangeOutputNames.toArray() )
        + "] have no corresponding outputs in ["
        + Arrays.toString(outputNames.toArray() )
        + "]",
        ExceptionClass.JSON);
    }
    
    // verify relations
    
    final double kMaxWeight = 1000.0;
    
    Set<String> relationOutputNames = new HashSet<String>();
    
    for (RelationJson relationJson : this.relations) {
      
      if (inputNames.contains(relationJson.input_name) == false) {
        
        throw new CodeException(
          "relations, input name ["
          + relationJson.input_name
          + "] doesn't belong to solution's inputs ["
          + Arrays.toString(inputNames.toArray() )
          + "]",
          ExceptionClass.JSON);
      }
      
      if (outputNames.contains(relationJson.output_name) == false) {
        
        throw new CodeException(
          "relations, output name ["
          + relationJson.output_name
          + "] doesn't belong to solution's outputs ["
          + Arrays.toString(outputNames.toArray() )
          + "]",
          ExceptionClass.JSON);
      }
      
      relationOutputNames.add(relationJson.output_name);
      
      StringVerifierInl.isOneOfString(
        "relation type",
        relationJson.relation,
        true,
        "DIRECTLY_PROPORTIONAL",
        "INVERSELY_PROPORTIONAL");
      
      ArgumentsInl.checkDoubleHasValue(
        "relation weight",
        relationJson.weight,
        ExceptionType.CODE_EXCEPTION);
      
      if (relationJson.weight > kMaxWeight) {
        
        throw new CodeException(
          "relation weight ["
          + relationJson.weight
          + "] can't be greater than ["
          + kMaxWeight
          + "]",
          ExceptionClass.JSON);
      }
    }
    
    // verify relative relations
    
    if (this.relations != null) {
      
      for (RelativeRelationJson relativeRelationJson :
           this.relative_relations) {
        
        ArgumentsInl.checkNotEmpty(
          "relative relation max output",
          relativeRelationJson.max_output,
          ExceptionType.CODE_EXCEPTION);
        ArgumentsInl.checkNotEmpty(
          "relative relation relative input",
          relativeRelationJson.relative_input,
          ExceptionType.CODE_EXCEPTION);
        ArgumentsInl.checkNotEmpty(
          "relative relation min output",
          relativeRelationJson.min_output,
          ExceptionType.CODE_EXCEPTION);
        
        if (inputNames.contains(
              relativeRelationJson.relative_input) == false) {
          
          throw new CodeException(
            "relative relation relative_input ["
            + relativeRelationJson.relative_input
            + "] must be one of the solution's inputs ["
            + Arrays.toString(inputNames.toArray() )
            + "]",
            ExceptionClass.JSON);
        }
        
        if (relativeRelationJson.max_output.compareTo(
              relativeRelationJson.min_output) == 0) {
          
          throw new CodeException(
            "a relative relation can't have the same value for both of "
            + "max_output ["
            + relativeRelationJson.max_output
            + "] and min_output ["
            + relativeRelationJson.min_output
            + "]",
            ExceptionClass.JSON);
        }
        
        if (relationOutputNames.contains(
              relativeRelationJson.max_output) == false) {
          
          throw new CodeException(
            "relative relation max_output ["
            + relativeRelationJson.max_output
            + "] must be one of the solution's relations' outputs ["
            + Arrays.toString(relationOutputNames.toArray() )
            + "]",
            ExceptionClass.JSON);
        }
        
        if (relationOutputNames.contains(
              relativeRelationJson.min_output) == false) {
          
          throw new CodeException(
            "relative relation min_output ["
            + relativeRelationJson.min_output
            + "] must be one of the solution's relations' outputs ["
            + Arrays.toString(relationOutputNames.toArray() )
            + "]",
            ExceptionClass.JSON);
        }
      }
    }
  }
}
