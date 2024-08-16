package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {
    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringSplitter stringSplitter = new StringSplitter();

        List<String> signatureParts = stringSplitter.splitByDelimiters(signatureString, List.of("(", ")"));
        if (signatureParts.isEmpty() || signatureParts.size() > 2) {
            throw new UnsupportedOperationException();
        }

        List<String> methodParts = stringSplitter.splitByDelimiters(signatureParts.get(0), List.of(" "));
        if (methodParts.size() < 2 || methodParts.size() > 3) {
            throw new UnsupportedOperationException();
        }

        List<MethodSignature.Argument> argumentList = new ArrayList<>();
        if (signatureParts.size() == 2) {
            List<String> arguments = stringSplitter.splitByDelimiters(signatureParts.get(1), List.of(","));
            for (String s : arguments) {
                List<String> argumentParts = stringSplitter.splitByDelimiters(s, List.of(" "));
                if (argumentParts.size() != 2) {
                    throw new UnsupportedOperationException();
                }

                argumentList.add(new MethodSignature.Argument(argumentParts.get(0), argumentParts.get(1)));
            }
        }

        MethodSignature methodSignature;
        if (methodParts.size() == 2) {
            methodSignature = new MethodSignature(methodParts.get(1), argumentList);
            methodSignature.setReturnType(methodParts.get(0));
        } else {
            methodSignature = new MethodSignature(methodParts.get(2), argumentList);
            methodSignature.setReturnType(methodParts.get(1));
            methodSignature.setAccessModifier(methodParts.get(0));
        }

        return methodSignature;
    }
}
