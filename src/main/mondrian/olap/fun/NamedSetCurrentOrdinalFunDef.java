/*
// $Id$
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2007 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.olap.fun;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.impl.AbstractIntegerCalc;
import mondrian.mdx.ResolvedFunCall;
import mondrian.mdx.NamedSetExpr;
import mondrian.olap.*;
import mondrian.resource.MondrianResource;

/**
 * Definition of the <code>&lt;Named Set&gt;.CurrentOrdinal</code> MDX builtin
 * function.
 *
 * @author jhyde
 * @version $Id$
 * @since Oct 19, 2008
 */
public class NamedSetCurrentOrdinalFunDef extends FunDefBase {
    static final NamedSetCurrentOrdinalFunDef instance =
        new NamedSetCurrentOrdinalFunDef();

    private NamedSetCurrentOrdinalFunDef() {
        super(
            "CurrentOrdinal",
            "Returns the ordinal of the current iteration through a named set.",
            "pix");
    }

    public Exp createCall(Validator validator, Exp[] args) {
        assert args.length == 1;
        final Exp arg0 = args[0];
        if (!(arg0 instanceof NamedSetExpr)) {
            throw MondrianResource.instance().NotANamedSet.ex();
        }
        return super.createCall(validator, args);
    }

    public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final Exp arg0 = call.getArg(0);
        assert arg0 instanceof NamedSetExpr : "checked this in createCall";
        final NamedSetExpr namedSetExpr = (NamedSetExpr) arg0;
        return new AbstractIntegerCalc(call, new Calc[0]) {
            public int evaluateInteger(Evaluator evaluator) {
                return namedSetExpr.getEval(evaluator).currentOrdinal();
            }
        };
    }
}

// End NamedSetCurrentOrdinalFunDef.java